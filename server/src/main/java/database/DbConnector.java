package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
    private final String propFileName;

    public DbConnector(String propFileName) {
        this.propFileName = propFileName;
    }

    public Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(propFileName));

        String host = properties.getProperty("dbHost");
        String login = properties.getProperty("dbLogin");
        String password = properties.getProperty("dbPassword");

        return DriverManager.getConnection(host, login, password);
    }
}
