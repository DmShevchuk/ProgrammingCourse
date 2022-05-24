package commands.command;

import commands.Command;
import commands.CommandManager;
import account.Client;
import run.ServerErrorHandler;

public class RemoveAllByHead extends Command {
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public RemoveAllByHead(Client client, CommandManager commandManager,
                           ServerErrorHandler errorHandler) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
//        try {
//            new DragonHead(Long.parseLong(commandManager.getArg()));
//            client.send(new Request.Builder()
//                    .setCommandName(this.getName())
//                    .setArgs(commandManager.getArg())
//                    .setRequestType(RequestType.RUN_COMMAND)
//                    .setAccount(client.getAccount())
//                    .build());
//            new ResponseReceiver().getResponse(client, commandLine);
//        } catch (ClassCastException | IOException e) {
//            errorHandler.handleServerError();
//        }
    }
}

