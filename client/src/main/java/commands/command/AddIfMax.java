package commands.command;

import collection.Dragon;
import commands.Command;
import commands.CommandManager;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;
import utils.ElementReadMode;

import java.io.IOException;


public class AddIfMax extends Command {
    private final CommandManager commandManager;
    private Client client;

    public AddIfMax(CommandLine commandLine, CommandManager commandManager) {
        super("add_if_max", "|| add a new element to the collection if its value" +
                " exceeds the value of the largest element of this collection", 0, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        this.client = client;

        commandLine.outLn("Enter an item to compare:");
        commandLine.outLn("(Empty string -> null)");
        commandLine.setElementMode(ElementReadMode.ELEMENT_COMPARE);

        ((Add) commandManager.getCommand("add")).addInit();
    }

    public void compare(Dragon.Builder newDragon) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).setDragonBuild(newDragon).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }


}
