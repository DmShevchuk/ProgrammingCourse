package commands.command;

import commands.Command;
import account.Client;
import run.ServerErrorHandler;

public class Info extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public Info(Client client, ServerErrorHandler errorHandler) {
        super("info",
                "|| display information about the collection (type, initialization date, number of elements)",
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
//                    .build());
//
//            Response response = new ResponseReceiver().getResponse(client, commandLine);
//            if (response != null) {
//                commandLine.outLn(response.getResult());
//            }
//        } catch (IOException e) {
//            errorHandler.handleServerError();
//        }
    }

}
