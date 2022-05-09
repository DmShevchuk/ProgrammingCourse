import collection.CollectionManager;
import commands.CommandManager;
import database.AccountHandler;
import database.CollectionLoader;
import database.DbConnector;
import database.DbManager;
import interaction.Response;
import interaction.ResponseStatus;
import run.ResponseSender;
import run.Server;
import run.ServerStarter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private final static int MAX_CONNECTED_USERS = 2;

    public static void main(String[] ar) throws IOException {
        ServerSocket serverSocket;
        Connection connection;

        try {
            String propPath = new File("server/src/main/resources/connection.properties")
                    .getAbsolutePath();
            serverSocket = new ServerStarter(propPath).start();
        } catch (IOException e) {
            System.out.printf("Unable to run server: %s!", e.getMessage());
            return;
        }

        try {
            String propPath = new File("server/src/main/resources/dbConfig.properties")
                    .getAbsolutePath();
            connection = new DbConnector(propPath).createConnection();
        } catch (SQLException e) {
            System.out.printf("Unable to connect to database %s!", e.getMessage());
            return;
        }

        CollectionManager collectionManager = CollectionManager.getInstance();
        DbManager dbManager = new DbManager(connection);

        Logger logger = Logger.getLogger(Main.class.getName());
        FileHandler fh = new FileHandler("./serverLog.log");
        logger.addHandler(fh);
        logger.log(Level.INFO, "Server is started, connection to database is established!");

        try {
            collectionManager.collectionInit(new CollectionLoader(connection).loadCollection());
            logger.log(Level.INFO, "Collection loaded!");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Unable to load collection!");
            return;
        }
        AccountHandler accountHandler = new AccountHandler(connection);
        CommandManager commandManager = new CommandManager(collectionManager, dbManager);
        ResponseSender responseSender = new ResponseSender();

        int threadQuantity = Thread.getAllStackTraces().keySet().size();

        while (true) {
            Socket socket = serverSocket.accept();
            if (Thread.getAllStackTraces().keySet().size() == threadQuantity + MAX_CONNECTED_USERS) {
                responseSender.send(new Response(ResponseStatus.FAIL,
                        "Too many clients on the server, please try again later!"), socket);
                socket.close();
            } else {
                logger.log(Level.INFO, "Client connected");
                new Thread(() -> new Server(socket, logger, accountHandler, commandManager, responseSender)).start();
            }
        }
    }
}
