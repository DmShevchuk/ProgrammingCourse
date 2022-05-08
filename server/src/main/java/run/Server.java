package run;

import collection.CollectionManager;
import commands.command.*;
import database.AccountHandler;
import database.DbManager;
import interaction.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final Socket socket;
    private final CollectionManager collectionManager;
    private final Connection connection;
    private final Logger logger;
    private Account account;
    private final DbManager dbManager;

    public Server(Socket socket, CollectionManager collectionManager, Logger logger, Connection connection,
                  DbManager dbManager) {
        this.socket = socket;
        this.collectionManager = collectionManager;
        this.logger = logger;
        this.connection = connection;
        this.dbManager = dbManager;

        run();
    }
    public void run() {
        saveAndExit();
        AccountHandler accountHandler = new AccountHandler(connection);
        try {
            while (true) {
                // Ожидание сообщения от клиента
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) objectInputStream.readObject();
                logger.log(Level.INFO, "New request received");
                if (request.getRequestType() == RequestType.RUN_COMMAND) {
                    send(runCommand(request), socket);
                    logger.log(Level.INFO, "Execution result sent to the client");
                } else {
                    System.out.println("Here!");
                    try {
                        if (request.getRequestType() == RequestType.AUTH) {
                            account = accountHandler.signIn(request.getAccount());
                        } else {
                            account = accountHandler.register(request.getAccount());
                        }
                        if (account != null) {
                            send(new Response(ResponseStatus.AUTH_RESULT, account), socket);
                            System.out.println("Success!");
                        } else {
                            throw new SQLException();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Not success!");
                        send(new Response(ResponseStatus.AUTH_RESULT, account), socket);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "The connection to the client has been terminated!");
        }
    }

    private Response runCommand(Request request) {
        logger.log(Level.INFO, String.format("Execution request '%s' command", request.getCommandName()));

        return switch (request.getCommandName()) {
            case "add" -> new Add(collectionManager, dbManager).execute(request);
            case "add_if_max" -> new AddIfMax(collectionManager, dbManager).execute(request);
            case "clear" -> new Clear(collectionManager, dbManager).execute(request);
            case "info" -> new Info(collectionManager).execute(request);
            case "min_by_id" -> new MinByID(collectionManager).execute(request);
            case "print_field_descending_weight" -> new PrintFieldDescendingWeight(collectionManager).execute(request);
            case "remove_all_by_head" -> new RemoveAllByHead(collectionManager, dbManager).execute(request);
            case "remove_by_id" -> new RemoveByID(collectionManager, dbManager).execute(request);
            case "remove_first" -> new RemoveFirst(collectionManager, dbManager).execute(request);
            case "show" -> new Show(collectionManager).execute(request);
            case "update" -> new UpdateId(collectionManager, dbManager).execute(request);
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
                connection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("The server has completed its work...");
        }));
    }
}