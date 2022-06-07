package commands.command;

import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import interaction.ResponseStatus;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;

public class MinByID extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;

    public MinByID(RequestSender sender, ResponseReceiver receiver) {
        super("min_by_id",
                "|| display any object from the collection whose id field value is the minimum",
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
        if (response != null) {
            return new Response(ResponseStatus.INFO, response.getDragon().toString());
        }
        return new Response(ResponseStatus.INFO, "Не удалось выполнить команду!");
    }
}
