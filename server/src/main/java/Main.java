import collection.CollectionManager;
import collection.Dragon;
import data.FileManager;
import data.ParserJSON;
import run.Server;

import java.io.*;
import java.net.*;
import java.util.LinkedList;


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
            System.out.println("Сервер запущен");
            CollectionManager collectionManager = CollectionManager.getInstance();
            collectionManager.collectionInit(loadCollection());

            while (true) {
                // ожидание подключения
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");

                new Server(socket, collectionManager);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null)
                serverSocket.close();
            System.exit(0);
        }
    }

    private static LinkedList<Dragon> loadCollection(){
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