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
import java.util.concurrent.atomic.AtomicInteger;

public class PrintFieldDescendingWeight extends Command{
    private ServerErrorHandler errorHandler;

    public PrintFieldDescendingWeight(CommandLine commandLine, ServerErrorHandler errorHandler) {
        super("print_field_descending_weight",
                "|| display the values of the weight field of all elements",
                0, commandLine);
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute(Client client) {
        try {
            client.send(new Request.Builder().setCommandName(this.getName()).build());
            Response response = new ResponseReceiver().getResponse(client, commandLine);
            if (response != null) {
                LinkedList<Dragon> dragonLinkedList = response.getDragonList();

                AtomicInteger index = new AtomicInteger();
                dragonLinkedList.
                        forEach(dragon ->
                                commandLine.outLn
                                        (String.format("%d)%s - %d кг", index.incrementAndGet(), dragon.getName(),
                                                dragon.getWeight())));
            }
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
