package commands.command;

import collection.CollectionManager;
import collection.DragonHead;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class RemoveFirst extends Command {
    private final CollectionManager collectionManager;

    public RemoveFirst(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollectionSize() == 0) {
            return new Response(ResponseStatus.FAIL, "В коллекции нет элементов!");
        }
        collectionManager.removeFirst();
        return new Response(ResponseStatus.SUCCESS, "Первый элемент коллекции удален!");
    }
}
