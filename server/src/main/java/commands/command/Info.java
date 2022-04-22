package commands.command;

import collection.*;
import commands.*;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class Info extends Command{
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.INFO, collectionManager.getInfo());
    }

}
