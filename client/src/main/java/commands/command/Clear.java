package commands.command;

import commands.Command;
import account.Client;
import run.ServerErrorHandler;

public class Clear extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public Clear(Client client, ServerErrorHandler errorHandler) {
        super("clear", "|| clear the collection", 0);
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
//        try {
//            client.send(new Request.Builder()
//                    .setCommandName(this.getName())
//                    .setRequestType(RequestType.RUN_COMMAND)
//                    .setAccount(client.getAccount())
//                    .build());
//            new ResponseReceiver().getResponse(client, commandLine);
//        } catch (IOException e) {
//            errorHandler.handleServerError();
//        }
    }
}
