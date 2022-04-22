package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class MinByID extends Command {
    private final CollectionManager collectionManager;
    public MinByID(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollectionSize() == 0){
            return new Response(ResponseStatus.FAIL, "В коллекции нет элементов!");
        }
        return new Response(ResponseStatus.INFO, collectionManager.getElementByID(collectionManager.getMinID()));
    }
}
