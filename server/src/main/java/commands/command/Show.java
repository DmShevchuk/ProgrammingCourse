package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.INFO, collectionManager.getCOLLECTION().toString());
    }
}
