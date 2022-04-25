package commands.command;

import collection.*;
import commands.*;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class Add extends Command{
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.addDragon(request.getDragonBuild());
        return new Response(ResponseStatus.SUCCESS, "Dragon added to collection!");
    }

}
