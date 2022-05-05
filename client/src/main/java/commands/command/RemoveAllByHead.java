package commands.command;

import collection.DragonHead;
import commands.*;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class RemoveAllByHead extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;

    public RemoveAllByHead(CommandLine commandLine, CommandManager commandManager, ServerErrorHandler errorHandler) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1, commandLine);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        try {
            new DragonHead(Long.parseLong(commandManager.getArg()));
            client.send(new Request.Builder().setCommandName(this.getName()).setArgs(commandManager.getArg()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            errorHandler.handleServerError();
        }
    }
}

