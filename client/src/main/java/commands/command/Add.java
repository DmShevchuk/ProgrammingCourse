package commands.command;

import collection.Dragon;
import commands.Command;
import commands.CommandManager;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;
import utils.DragonCreator;

import java.io.IOException;

public class Add extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;
    private Client client;

    public Add(CommandLine commandLine, CommandManager commandManager, ServerErrorHandler errorHandler) {
        super("add", "|| add a new element to the collection", 0, commandLine);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        this.client = client;
        commandLine.outLn("Adding a dragon to the collection\n(empty string = null, '_quit_' to exit)");

        DragonCreator dragonCreator = new DragonCreator(commandLine);
        Dragon.Builder newDragon = dragonCreator.getNewDragon();

        if (newDragon == null) return;

        try {
            client.send(new Request.Builder().setCommandName(this.getName()).setDragonBuild(newDragon).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
