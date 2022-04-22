package commands.command;

import collection.CollectionManager;
import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.util.LinkedList;

public class PrintFieldDescendingWeight extends Command{
    private final CollectionManager collectionManager;

    public PrintFieldDescendingWeight(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollectionSize() == 0){
            return new Response(ResponseStatus.FAIL, "В коллекции нет элементов!");
        }
        return new Response(ResponseStatus.INFO, collectionManager.sortByWeight());
    }
}
