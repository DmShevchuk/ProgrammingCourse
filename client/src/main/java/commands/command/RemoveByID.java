package commands.command;

import commands.Command;
import commands.CommandManager;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class RemoveByID extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;

    public RemoveByID(CommandLine commandLine, CommandManager commandManager, ServerErrorHandler errorHandler) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1, commandLine);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        try {
            Integer.parseInt(commandManager.getARG());
            client.send(new Request.Builder().setCommandName(this.getName()).setArgs(commandManager.getARG()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            errorHandler.handleServerError();
        }
    }
}
