package commands.command;

import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class RemoveByID extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public RemoveByID(RequestSender sender, ResponseReceiver receiver) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName(this.getName())
                .setArgs(args.toString())
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}
