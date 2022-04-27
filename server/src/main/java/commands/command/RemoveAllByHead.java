package commands.command;

import collection.CollectionManager;
import collection.DragonHead;
import commands.Command;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

public class RemoveAllByHead extends Command {
    private final CollectionManager collectionManager;

    public RemoveAllByHead(CollectionManager collectionManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollectionSize() == 0) {
            return new Response(ResponseStatus.FAIL, "Collection is empty!");
        }
        collectionManager.removeByHead(new DragonHead(Long.parseLong(request.getArgs())));
        return new Response(ResponseStatus.SUCCESS, String.format("Dragons with head size=%s removed!", request.getArgs()));
    }
}