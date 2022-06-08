package commands.command;

import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class UpdateCollection extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public UpdateCollection(RequestSender sender, ResponseReceiver receiver) {
        super("update_collection", "|| get new version of collection from server", 0);
        this.sender = sender;
        this.receiver = receiver;
    }
    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName("update_collection")
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }
}
