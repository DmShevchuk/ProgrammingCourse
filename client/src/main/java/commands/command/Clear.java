package commands.command;

import commands.Command;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class Clear extends Command {
    private ServerErrorHandler errorHandler;

    public Clear(CommandLine commandLine, ServerErrorHandler errorHandler) {
        super("clear", "|| clear the collection", 0, commandLine);
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}