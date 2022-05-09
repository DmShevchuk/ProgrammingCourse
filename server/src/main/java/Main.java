import collection.CollectionManager;
import database.CollectionLoader;
import database.DbConnector;
import database.DbManager;
import run.Server;
import run.ServerStarter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] ar) throws IOException {
        ServerSocket serverSocket;
        Connection connection;

        try {
            String propPath = new File("server/src/main/resources/connection.properties").getAbsolutePath();
            serverSocket = new ServerStarter(propPath).start();
        } catch (IOException e){
            System.out.printf("Unable to run server: %s!", e.getMessage());
            return;
        }

        try {
            String propPath = new File("server/src/main/resources/dbConfig.properties").getAbsolutePath();
            connection = new DbConnector(propPath).createConnection();
        }catch (SQLException e){
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
            logger.log(Level.WARNING,"Unable to load collection!");
            return;
        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {
            // ожидание подключения
            Socket socket = serverSocket.accept();
            logger.log(Level.INFO, "Client connected");
            executorService.execute(new Server(socket, collectionManager, logger, connection, dbManager));
        }
    }
}
