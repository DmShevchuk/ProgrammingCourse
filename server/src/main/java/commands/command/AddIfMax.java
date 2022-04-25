package commands.command;

import collection.CollectionManager;
import collection.Dragon;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;


public class AddIfMax extends Command{
    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (compare(request.getDragonBuild())){
            collectionManager.addDragon(request.getDragonBuild());
            return new Response(ResponseStatus.SUCCESS, "Dragon added to collection!");
        }
        return new Response(ResponseStatus.FAIL, "The dragon is not superior the largest dragon!");
    }

    public boolean compare(Dragon.Builder newDragon) {
        if(collectionManager.getCollectionSize() == 0){
            return true;
        }
        Dragon dragon = collectionManager.getMaxElement();
        return dragon.compareTo(newDragon.build()) < 0;
    }


}
