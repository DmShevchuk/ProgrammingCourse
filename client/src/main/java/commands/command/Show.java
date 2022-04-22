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

public class Show extends Command implements UsesCollectionManager {
    public Show(CommandLine commandLine) {
        super("show", "|| display all elements of the collection in string representation", 0, commandLine);
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());
            Response response = new ResponseReceiver().getResponse(client, commandLine);
            if (response != null) {
                commandLine.outLn(response.getResult());
            }
        } catch (IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }
}
