package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DbConnector {
    private Connection connection;
    private final String db_url = "jdbc:postgresql://localhost:2345/studs";

    public DbConnector() {
    }

    public Connection createConnection() {
        //TODO обработка невозможности соединения
        Scanner scanner = new Scanner(System.in);
        //TODO скрыть ввод пароля
        while (connection == null) {
            System.out.print("Введите логин: ");
            String user = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            try {
                getConnection(user, password);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private void getConnection(String user, String password) throws SQLException {
        connection = DriverManager.getConnection(db_url, user, password);
    }
}
