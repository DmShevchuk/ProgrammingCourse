import collection.CollectionManager;
import commands.CommandManager;
import database.AccountHandler;
import database.CollectionLoader;
import database.DbConnector;
import database.DbManager;
import database.run.ResponseSender;
import database.run.Server;
import database.run.ServerStarter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    public static void main(String[] ar) throws IOException {
        ServerSocket serverSocket;
        Connection connection;

        // Запуск сервера
        try {
            String propPath = new File("server/src/main/resources/connection.properties")
                    .getAbsolutePath();
            serverSocket = new ServerStarter(propPath).start();
        } catch (IOException e) {
            System.out.printf("Unable to run server: %s!", e.getMessage());
            return;
        }

        // Подключение к БД
        try {
            String propPath = new File("server/src/main/resources/dbConfig.properties")
                    .getAbsolutePath();
            connection = new DbConnector(propPath).createConnection();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database," +
                    " check host (../resources/dbConfig.properties) availability!");
            return;
        }

        Logger logger = Logger.getLogger(Main.class.getName());
        logger.log(Level.INFO, "Server is started, connection to database is established!");

        CollectionManager collectionManager = CollectionManager.getInstance();
        DbManager dbManager = new DbManager(connection);

        try {
            CollectionLoader loader = new CollectionLoader(connection);
            loader.initializeTables();
            collectionManager.collectionInit(loader.loadCollection());
            logger.log(Level.INFO, "Collection loaded!");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Unable to load collection!");
            return;
        }

        AccountHandler accountHandler = new AccountHandler(connection);
        CommandManager commandManager = new CommandManager(collectionManager, dbManager);
        ResponseSender responseSender = new ResponseSender();
        while (true) {
            Socket socket = serverSocket.accept();
            logger.log(Level.INFO, "Client connected");
            new Thread(() -> new Server(socket, logger, accountHandler, commandManager, responseSender)).start();
            accountHandler.setConnectedAccounts(+1);

        }
    }
}
