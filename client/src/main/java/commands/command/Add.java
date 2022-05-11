package commands.command;

import collection.Dragon;
import commands.Command;
import exceptions.DragonInputInterruptedException;
import interaction.Request;
import interaction.RequestType;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;
import utils.DragonCreator;

import java.io.IOException;

public class Add extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public Add(CommandLine commandLine, Client client, ServerErrorHandler errorHandler) {
        super("add", "|| add a new element to the collection", 0, commandLine);
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
        commandLine.outLn("Adding a dragon to the collection\n(empty string = null, '_quit_' to exit)");

        DragonCreator dragonCreator = new DragonCreator(commandLine);
        Dragon.Builder newDragon;
        try {
            newDragon = dragonCreator.getNewDragon();
        } catch (DragonInputInterruptedException e) {
            return;
        }
        newDragon.setOwnerId(client.getAccount().getId());
        try {
            client.send(new Request.Builder()
                    .setCommandName(this.getName())
                    .setDragonBuild(newDragon)
                    .setRequestType(RequestType.RUN_COMMAND)
                    .setAccount(client.getAccount())
                    .build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
