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


public class UpdateId extends Command{
    private Integer currentId;
    private final CommandManager commandManager;
    private Client client;

    public UpdateId(CommandLine commandLine, CommandManager commandManager) {
        super("update",
                "||{id}  update the value of the collection element whose id is equal to the given one",
                1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        this.client = client;
        try {
            currentId = Integer.parseInt(commandManager.getARG());
            commandLine.outLn("To enter null, use an empty string.");

            commandLine.setElementMode(ElementReadMode.ELEMENT_UPDATE);
            ((Add) commandManager.getCommand("add")).addInit();

        } catch (ClassCastException e) {
            commandLine.errorOut("Impossible to get id=" + commandManager.getARG());
        }
    }

    public void update(Dragon.Builder dragon) {
        try {
            client.send(new Request.Builder().
                    setCommandName(this.getName()).
                    setArgs(currentId.toString()).
                    setDragonBuild(dragon).
                    build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }
}
