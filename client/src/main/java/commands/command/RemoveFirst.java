package commands.command;


import commands.Command;
import account.Client;
import run.ServerErrorHandler;

public class RemoveFirst extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public RemoveFirst(Client client, ServerErrorHandler errorHandler) {
        super("remove_first",
                "|| remove the first element from the collection",
                0);
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
