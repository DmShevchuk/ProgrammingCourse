package commands.command;

import collection.CollectionManager;
import commands.Command;
import commands.UsesCollectionManager;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class RemoveFirst extends Command implements UsesCollectionManager {
    public RemoveFirst(CommandLine commandLine) {
        super("remove_first",
                "|| remove the first element from the collection",
                0, commandLine);
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }
}
