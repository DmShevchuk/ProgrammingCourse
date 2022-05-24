package commands.command;

import commands.Command;
import account.Client;
import run.ServerErrorHandler;

public class Show extends Command {
    private ServerErrorHandler errorHandler;
    private final Client client;

    public Show(Client client, ServerErrorHandler errorHandler) {
        super("show", "|| display all elements of the collection in string representation", 0);
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
//            Response response = new ResponseReceiver().getResponse(client, commandLine);
//            if (response != null) {
//                LinkedList<Dragon> dragonLinkedList = response.getDragonList();
//                for(Dragon d: dragonLinkedList){
//                    // Вывод дракона белым, он если принадлежит текущему пользователю
//                    if(d.getOwnerId().equals(client.getAccount().getId())){
//                        commandLine.showOutLn("\u001b[37;1m", d.toString());
//                        // В другом случае серым
//                    }else{
//                        commandLine.showOutLn("\u001b[30;1m", d.toString());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            errorHandler.handleServerError();
//        }
    }
}
