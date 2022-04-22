package commands.command;

import commands.Command;
import commands.UsesCollectionManager;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class PrintFieldDescendingWeight extends Command implements UsesCollectionManager {
    public PrintFieldDescendingWeight(CommandLine commandLine) {
        super("print_field_descending_weight",
                "|| display the values of the weight field of all elements",
                0, commandLine);
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
            e.printStackTrace();
        }
    }
}
