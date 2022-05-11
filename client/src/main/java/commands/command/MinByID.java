package commands.command;

import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;

public class MinByID extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public MinByID(CommandLine commandLine, Client client, ServerErrorHandler errorHandler) {
        super("min_by_id",
                "|| display any object from the collection whose id field value is the minimum",
                0, commandLine);
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            client.send(new Request.Builder()
                    .setCommandName(this.getName())
                    .setRequestType(RequestType.RUN_COMMAND)
                    .build());
            Response response = new ResponseReceiver().getResponse(client, commandLine);
            if (response != null) {
                commandLine.outLn(response.getDragon().toString());
            }
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
