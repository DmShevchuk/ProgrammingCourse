package commands.command;

import commands.Command;
import account.Client;
import run.ServerErrorHandler;

public class PrintFieldDescendingWeight extends Command {
    private final ServerErrorHandler errorHandler;
    private final Client client;

    public PrintFieldDescendingWeight(Client client, ServerErrorHandler errorHandler) {
        super("print_field_descending_weight",
                "|| display the values of the weight field of all elements",
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
//            Response response = new ResponseReceiver().getResponse(client, commandLine);
//            if (response != null) {
//                LinkedList<Dragon> dragonLinkedList = response.getDragonList();
//
//                AtomicInteger index = new AtomicInteger();
//                dragonLinkedList.
//                        forEach(dragon -> commandLine.outLn(String.format("%d)%s - %d kg",
//                                        index.incrementAndGet(),
//                                        dragon.getName(),
//                                        dragon.getWeight())));
//            }
//        } catch (IOException e) {
//            errorHandler.handleServerError();
//        }
    }
}
