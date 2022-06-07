package commands.command;

import collection.Dragon;
import commands.Command;
import account.Client;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;


public class AddIfMax extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public AddIfMax(RequestSender sender, ResponseReceiver receiver) {
        super("add_if_max", "|| add a new element to the collection if its value" +
                " exceeds the value of the largest element of this collection", 0);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName("add_if_max")
                .setDragonBuild((Dragon.Builder) args)
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}
