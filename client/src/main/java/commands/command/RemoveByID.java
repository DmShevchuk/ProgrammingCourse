package commands.command;

import commands.Command;
import commands.CommandManager;
import interaction.Request;
import interaction.RequestType;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class RemoveByID extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public RemoveByID(CommandLine commandLine, Client client, CommandManager commandManager, ServerErrorHandler errorHandler) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1, commandLine);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            Integer.parseInt(commandManager.getArg());
            client.send(new Request.Builder()
                    .setCommandName(this.getName())
                    .setArgs(commandManager.getArg())
                    .setRequestType(RequestType.RUN_COMMAND)
                    .setAccount(client.getAccount())
                    .build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            errorHandler.handleServerError();
        }
    }
}
