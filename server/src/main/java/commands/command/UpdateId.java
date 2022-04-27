package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;


public class UpdateId extends Command {
    private CollectionManager collectionManager;

    public UpdateId(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        int id = Integer.parseInt(request.getArgs());
        if (collectionManager.checkExistingID(id)) {
            collectionManager.updateElementById(id, request.getDragonBuild().build());
            return new Response(ResponseStatus.SUCCESS, "Item updated successfully!");
        }
        return new Response(ResponseStatus.FAIL, "This element no longer exists!");
    }

}
