package commands.command;

import collection.CollectionManager;
import collection.DragonHead;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class RemoveByID extends Command {
    private final CollectionManager collectionManager;

    public RemoveByID(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        int id = Integer.parseInt(request.getArgs());

        if (collectionManager.getCollectionSize() == 0 || !collectionManager.checkExistingID(id)) {
            return new Response(ResponseStatus.FAIL, String.format("В коллекции нет элементов с id=%d!", id));
        }

        collectionManager.deleteElementByID(id);
        return new Response(ResponseStatus.SUCCESS, String.format("Элемент с id=%d удален!", id));
    }
}
