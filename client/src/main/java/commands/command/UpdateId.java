package commands.command;

import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;


public class UpdateId extends Command {
    private final RequestSender sender;
    private final ResponseReceiver receiver;
    private Integer id;


    public UpdateId(RequestSender sender, ResponseReceiver receiver) {
        super("update",
                "||{id}  update the value of the collection element whose id is equal to the given one",
                1);
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public <T> Response execute(T args) throws IOException {
        sender.send(new Request.Builder()
                .setCommandName("update")
                .setArgs(id.toString())
                .setDragonBuild((Dragon.Builder) args)
                .setRequestType(RequestType.RUN_COMMAND));
        return receiver.getResponse();
    }

    @Override
    public <T> void setAdditionalArgs(T args){
        this.id = (Integer) args;
    }
}
