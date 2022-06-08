package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class UpdateCollection extends Command {
    private final CollectionManager collectionManager;

    public UpdateCollection(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.SUCCESS, collectionManager.getCollection());
    }
}
