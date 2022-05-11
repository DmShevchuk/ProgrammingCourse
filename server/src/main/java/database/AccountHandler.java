package database;

import interaction.Account;
import interaction.Request;
import interaction.RequestType;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/*
    Класс, отвечающий за вход и регистрацию пользователей
**/

public class AccountHandler {
    private final Connection connection;
    private volatile Integer connectedAccounts = 0;

    public AccountHandler(Connection connection) {
        this.connection = connection;
    }

    /*
        Метод, инициирующий процесс входа/регистрации
    **/
    public synchronized Account passAuth(Request request) {
        try {
            if (request.getRequestType() == RequestType.AUTH) {
                return signIn(request.getAccount());
            }
            return register(request.getAccount());
        } catch (SQLException e) {
            return null;
        }

    }

    /*
        Генерация "соли"
        Она хранится как отдельное поле в таблице users
    **/
    private String generateSalt() {
        byte[] array = new byte[16];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    /*
        Хеширование пароля по алгоритму SHA-224
        и добавление к нему "перца"
    **/
    private String hashPassword(String password, String salt) {
        String pepper = "AxmoOplam-K01LSla";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest((pepper + password + salt).getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return no.toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
        Регистрация пользователя
        В конце вызов #signIn(Account account),
        чтобы получить id аккаунта, которое назначается БД
    **/
    public Account register(Account account) throws SQLException {
        String login = account.getLogin();
        String password = account.getHashedPassword();
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO users (name, password, salt) VALUES (?, ?, ?)");

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setString(3, salt);

        preparedStatement.executeUpdate();

        return signIn(new Account(login, password));
    }

    /*
        Вход в аккаунт
    **/
    public Account signIn(Account account) throws SQLException {
        String login = account.getLogin();
        String password = account.getHashedPassword();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM users WHERE name = ?");

        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String hashedPassword = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            if (hashedPassword.equals(hashPassword(password, salt))) {
                return new Account(id, login, password);
            }
        }

        return null;
    }

    public void setConnectedAccounts(int val) {
        synchronized (connectedAccounts) {
            connectedAccounts += val;
        }
    }

    public synchronized int getConnectedAccounts() {
        synchronized (connectedAccounts) {
            return connectedAccounts;
        }
    }
}
