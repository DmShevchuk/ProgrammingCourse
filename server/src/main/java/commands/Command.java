package commands;

import collection.CollectionManager;
import interaction.Request;
import interaction.Response;

public abstract class Command {

    protected Command(CollectionManager collectionManager) {}

    public abstract Response execute(Request request);
}
