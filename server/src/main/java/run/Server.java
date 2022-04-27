package run;

import collection.CollectionManager;
import commands.command.*;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final Socket socket;
    private final CollectionManager collectionManager;
    private final Logger logger;

    public Server(Socket socket, CollectionManager collectionManager, Logger logger) {
        this.socket = socket;
        this.collectionManager = collectionManager;
        this.logger = logger;

        run();
    }

    public void run() {
        saveAndExit();
        try {
            while (true) {
                // Ожидание сообщения от клиента
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) objectInputStream.readObject();
                logger.log(Level.INFO, "New request received");
                send(runCommand(request), socket);
                logger.log(Level.INFO, "Execution result sent to the client");
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "The connection to the client has been terminated!");
        }
    }

    private Response runCommand(Request request) {
        logger.log(Level.INFO, String.format("Execution request '%s' command", request.getCommandName()));

        return switch (request.getCommandName()) {
            case "add" -> new Add(collectionManager).execute(request);
            case "add_if_max" -> new AddIfMax(collectionManager).execute(request);
            case "clear" -> new Clear(collectionManager).execute(request);
            case "info" -> new Info(collectionManager).execute(request);
            case "min_by_id" -> new MinByID(collectionManager).execute(request);
            case "print_field_descending_weight" -> new PrintFieldDescendingWeight(collectionManager).execute(request);
            case "remove_all_by_head" -> new RemoveAllByHead(collectionManager).execute(request);
            case "remove_by_id" -> new RemoveByID(collectionManager).execute(request);
            case "remove_first" -> new RemoveFirst(collectionManager).execute(request);
            case "show" -> new Show(collectionManager).execute(request);
            case "update" -> new UpdateId(collectionManager).execute(request);
            default -> null;
        };
    }

    public void send(Response response, Socket socket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(response);

        socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array());
        byteArrayOutputStream.writeTo(socket.getOutputStream());
    }

    private void saveAndExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                send(new Response(ResponseStatus.RESET_CONNECTION, "Force disconnect"), socket);
                new Save(collectionManager, logger).execute(new Request.Builder().build());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            new Save(collectionManager, logger).execute(new Request.Builder().build());
            System.out.println("The server has completed its work...");
        }));
    }
}