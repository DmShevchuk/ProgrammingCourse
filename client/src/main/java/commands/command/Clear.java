package commands.command;

import commands.Command;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class Clear extends Command {
    public Clear(CommandLine commandLine) {
        super("clear", "|| clear the collection", 0, commandLine);
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
