package commands.command;

import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;
import java.util.LinkedList;

public class Show extends Command {
    private ServerErrorHandler errorHandler;

    public Show(CommandLine commandLine, ServerErrorHandler errorHandler) {
        super("show", "|| display all elements of the collection in string representation", 0, commandLine);
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());
            Response response = new ResponseReceiver().getResponse(client, commandLine);
            if (response != null) {
                LinkedList<Dragon> dragonLinkedList = response.getDragonList();
                dragonLinkedList.
                        forEach(dragon -> commandLine.outLn(dragon.toString()));
            }
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
