package commands;

import collection.CollectionManager;
import interaction.Request;
import interaction.Response;

public abstract class Command {
    private final CollectionManager collectionManager;

    protected Command(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public abstract Response execute(Request request);
}
