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

public class Show extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public Show(RequestSender sender, ResponseReceiver receiver) {
        super("show", "|| display all elements of the collection in string representation", 0);
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
            for (Dragon d : dragonLinkedList) {
                dragonsString.append(d.toString()).append("\n");
            }
        }
        return new Response(ResponseStatus.SUCCESS, dragonsString.toString());
    }
}
