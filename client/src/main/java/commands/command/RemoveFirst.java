package commands.command;


import commands.Command;
import account.Client;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class RemoveFirst extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public RemoveFirst(RequestSender sender, ResponseReceiver receiver) {
        super("remove_first",
                "|| remove the first element from the collection",
                0);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName("remove_first")
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}
