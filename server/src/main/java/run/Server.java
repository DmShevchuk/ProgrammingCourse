package run;

import collection.CollectionManager;
import commands.CommandManager;
import database.AccountHandler;
import exceptions.IncorrectLoginDataException;
import interaction.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Класс, реализующий логику работы с подключением пользователя
**/

public class Server implements Runnable {
    private final Socket socket;
    private final AccountHandler accountHandler;
    private final Logger logger;
    private final CommandManager commandManager;
    private final ResponseSender responseSender;

    public Server(Socket socket,
                  Logger logger,
                  AccountHandler accountHandler,
                  CommandManager commandManager,
                  ResponseSender responseSender) {
        this.socket = socket;
        this.logger = logger;
        this.accountHandler = accountHandler;
        this.commandManager = commandManager;
        this.responseSender = responseSender;
        run();
    }


    public void run() {
        CollectionManager collectionManager = CollectionManager.getInstance();
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) objectInputStream.readObject();

                Response response;
                if (request.getRequestType() == RequestType.RUN_COMMAND) {
                    logger.log(Level.INFO, "New request to run command " + request.getCommandName());
                    response = commandManager.runCommand(request);
                } else {
                    logger.log(Level.INFO, "New request to login");

                    try {
                        Account account = accountHandler.passAuth(request);
                        response = new Response(ResponseStatus.AUTH_RESULT, account, collectionManager.getCollection());
                    } catch (IncorrectLoginDataException e) {
                        response = new Response(ResponseStatus.FAIL, e.getMessage());
                    } catch (SQLException e) {
                        response = new Response(ResponseStatus.FAIL, "Unable to login!");
                    }

                }
                try {
                    executor.execute(responseSender.send(response, socket));
                } catch (NullPointerException ignored) {
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.INFO, "Client disconnected!");
            accountHandler.setConnectedAccounts(-1);
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

}