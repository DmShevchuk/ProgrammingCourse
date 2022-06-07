package commands.command;

import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import interaction.ResponseStatus;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintFieldDescendingWeight extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public PrintFieldDescendingWeight(RequestSender sender, ResponseReceiver receiver) {
        super("print_field_descending_weight",
                "|| display the values of the weight field of all elements",
                0);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName(this.getName())
                .setRequestType(RequestType.RUN_COMMAND));
        Response response = receiver.getResponse();
        StringBuilder dragonsString = new StringBuilder();
        if (response != null) {
            LinkedList<Dragon> dragonLinkedList = response.getDragonList();
            for (Dragon dragon : dragonLinkedList) {
                AtomicInteger index = new AtomicInteger();
                dragonsString.append(String.format("%d)%s - %d kg\n",
                        index.incrementAndGet(),
                        dragon.getName(),
                        dragon.getWeight()));
            }
            return new Response(ResponseStatus.SUCCESS, dragonsString.toString());
        }
        return new Response(ResponseStatus.FAIL, "Не удалось выполнить команду!");
    }
}
