import collection.CollectionManager;
import collection.Dragon;
import data.*;
import run.Server;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final String FILENAME = "collection.json";
    private static final int PORT = 12012;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] ar) throws IOException {
        ServerSocket serverSocket = null;
        try {
            InetAddress inetAddress;
            inetAddress = InetAddress.getByName(HOST);
            serverSocket = new ServerSocket(PORT, 0, inetAddress);

            Logger logger = Logger.getLogger(Main.class.getName());
            FileHandler fh = new FileHandler("./serverLog.log");
            logger.addHandler(fh);
            logger.log(Level.INFO, "Server started");

            CollectionManager collectionManager = CollectionManager.getInstance();
            collectionManager.collectionInit(loadCollection());

            while (true) {
                // ожидание подключения
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "Client connected");

                new Server(socket, collectionManager, logger);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (serverSocket != null)
                serverSocket.close();
            System.exit(0);
        }
    }

    private static LinkedList<Dragon> loadCollection() {
        FileManager fileManager = new FileManager();

        try {
            fileManager.canRead(FILENAME);
            String jsonString = fileManager.read(FILENAME);
            ParserJSON parser = new ParserJSON();
            return parser.parse(jsonString);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}