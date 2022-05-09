package run;

import commands.CommandManager;
import database.AccountHandler;
import interaction.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
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
                    Account account = accountHandler.passAuth(request);
                    if (account == null) {
                        response = new Response(ResponseStatus.FAIL, "Unable to login!");
                    } else {
                        response = new Response(ResponseStatus.AUTH_RESULT, account);
                    }
                }
                new Thread(() -> responseSender.send(response, socket)).start();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.INFO, "Client disconnected!");
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
    }

}