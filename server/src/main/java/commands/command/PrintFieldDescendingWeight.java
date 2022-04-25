package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class PrintFieldDescendingWeight extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingWeight(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollectionSize() == 0) {
            return new Response(ResponseStatus.FAIL, "Collection is empty!");
        }
        return new Response(ResponseStatus.INFO, collectionManager.sortByWeight());
    }
}
