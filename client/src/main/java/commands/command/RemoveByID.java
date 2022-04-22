package commands.command;
import collection.DragonHead;
import commands.Command;
import commands.CommandManager;
import commands.UsesCollectionManager;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class RemoveByID extends Command implements UsesCollectionManager {
    private final CommandManager commandManager;
    public RemoveByID(CommandLine commandLine, CommandManager commandManager) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        try {
            Integer.parseInt(commandManager.getARG());
            client.send(new Request.Builder().setCommandName(this.getName()).setArgs(commandManager.getARG()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }
}
