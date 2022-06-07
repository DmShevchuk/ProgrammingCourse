package commands.command;

import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class RemoveAllByHead extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public RemoveAllByHead(RequestSender sender, ResponseReceiver receiver) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName("remove_all_by_head")
                .setArgs(args.toString())
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}

