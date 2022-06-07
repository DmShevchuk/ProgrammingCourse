package commands.command;

import commands.Command;
import account.Client;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class Info extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public Info(RequestSender sender, ResponseReceiver receiver) {
        super("info",
                "|| display information about the collection (type, initialization date, number of elements)",
                0);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName(this.getName())
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}
