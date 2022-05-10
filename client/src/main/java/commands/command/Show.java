package commands.command;

import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.Client;
import run.ResponseReceiver;
import run.ServerErrorHandler;
import utils.CommandLine;

import java.io.IOException;
import java.util.LinkedList;

public class Show extends Command {
    private ServerErrorHandler errorHandler;
    private final Client client;

    public Show(CommandLine commandLine, Client client, ServerErrorHandler errorHandler) {
        super("show", "|| display all elements of the collection in string representation", 0, commandLine);
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
                LinkedList<Dragon> dragonLinkedList = response.getDragonList();
                for(Dragon d: dragonLinkedList){
                    // Вывод дракона белым, он если принадлежит текущему пользователю
                    if(d.getOwnerId().equals(client.getAccount().getId())){
                        commandLine.showOutLn("\u001b[37;1m", d.toString());
                        // Иначе серым
                    }else{
                        commandLine.showOutLn("\u001b[30;1m", d.toString());
                    }
                }
            }
        } catch (IOException e) {
            errorHandler.handleServerError();
        }
    }
}
