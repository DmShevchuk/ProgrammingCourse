package commands.command;

import collection.Dragon;
import commands.Command;
import commands.CommandManager;
import account.Client;
import run.ServerErrorHandler;


public class UpdateId extends Command {
    private Integer currentId;
    private final CommandManager commandManager;
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public UpdateId(Client client, CommandManager commandManager, ServerErrorHandler errorHandler) {
        super("update",
                "||{id}  update the value of the collection element whose id is equal to the given one",
                1);
        this.commandManager = commandManager;
        this.errorHandler = errorHandler;
        this.client = client;
    }

    @Override
    public void execute() {
//        try {
//            currentId = Integer.parseInt(commandManager.getArg());
//            DragonCreator dragonCreator = new DragonCreator(commandLine);
//            Dragon.Builder newDragon;
//            try {
//                newDragon = dragonCreator.getNewDragon();
//            }catch (DragonInputInterruptedException e){
//                return;
//            }
//
//            update(newDragon);
//
//        } catch (ClassCastException e) {
//            commandLine.errorOut("Impossible to get id=" + commandManager.getArg());
//        }
    }

    public void update(Dragon.Builder dragon) {
//        try {
//            client.send(new Request.Builder().
//                    setCommandName(this.getName())
//                    .setArgs(currentId.toString())
//                    .setDragonBuild(dragon)
//                    .setRequestType(RequestType.RUN_COMMAND)
//                    .setAccount(client.getAccount())
//                    .build());
//            new ResponseReceiver().getResponse(client, commandLine);
//        } catch (IOException e) {
//            errorHandler.handleServerError();
//        }
    }
}
