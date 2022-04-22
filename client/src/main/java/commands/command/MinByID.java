package commands.command;

import commands.Command;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class MinByID extends Command {
    public MinByID(CommandLine commandLine) {
        super("min_by_id",
                "|| display any object from the collection whose id field value is the minimum",
                0, commandLine);
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());

            Response response = new ResponseReceiver().getResponse(client, commandLine);
            if (response != null) {
                commandLine.outLn(response.getDragon().toString());
            }
        } catch (IOException e) {
            commandLine.errorOut(e.getMessage());
        }
    }
}
