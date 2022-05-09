package run;

import commands.CommandManager;
import database.AccountHandler;
import interaction.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    private final Socket socket;
    private final AccountHandler accountHandler;
    private final Logger logger;
    private final CommandManager commandManager;
    private final ResponseSender responseSender;

    public Server(Socket socket, Logger logger,
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

                logger.log(Level.INFO, "New request received");
                Response response = null;
                if (request.getRequestType() == RequestType.RUN_COMMAND) {
                    response = commandManager.runCommand(request);
                } else {
                    Account account = accountHandler.passAuth(request);
                    if (account == null) {
                        response = new Response(ResponseStatus.FAIL, "Unable to login!");
                    } else {
                        response = new Response(ResponseStatus.AUTH_RESULT, account);
                    }
                }
                responseSender.send(response, socket);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.INFO, "Client disconnected!");
        }
    }

}