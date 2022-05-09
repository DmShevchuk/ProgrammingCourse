package database;

import interaction.Account;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AccountHandler {
    private final Connection connection;

    public AccountHandler(Connection connection) {
        this.connection = connection;
    }

    private String generateSalt() {
        byte[] array = new byte[16];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

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

    public Account register(Account account) throws SQLException {
        String login = account.getLogin();
        String password = account.getHashedPassword();
        String sqlQuery = "INSERT INTO users (name, password, salt) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        //TODO свое исключение
        if (hashedPassword == null) throw new RuntimeException();
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setString(3, salt);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return signIn(new Account(login, password));
    }

    public Account signIn(Account account) throws SQLException {
        String login = account.getLogin();
        String password = account.getHashedPassword();
        String sqlQuery = "SELECT * FROM users WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
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
}
