package commands.command;

import collection.Dragon;
import commands.Command;
import commands.UsesCollectionManager;
import interaction.Request;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

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
                LinkedList<Dragon> dragonLinkedList = response.getDragonList();

                AtomicInteger index = new AtomicInteger();
                dragonLinkedList.
                        forEach(dragon ->
                                commandLine.outLn
                                        (String.format("%d) %s - %d кг", index.incrementAndGet(), dragon.getName(),
                                                dragon.getWeight())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
