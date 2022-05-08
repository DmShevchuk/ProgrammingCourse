package commands.command;

import collection.DragonHead;
import commands.Command;
import commands.CommandManager;
import interaction.Request;
import interaction.RequestType;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class RemoveAllByHead extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public RemoveAllByHead(CommandLine commandLine, Client client, CommandManager commandManager,
                           ServerErrorHandler errorHandler) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1, commandLine);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            new DragonHead(Long.parseLong(commandManager.getArg()));
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

