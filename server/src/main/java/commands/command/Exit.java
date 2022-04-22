package commands.command;

import collection.CollectionManager;
import commands.Command;
import interaction.Request;
import interaction.Response;

public class Exit extends Command {
    public Exit(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Response execute(Request request) {
        System.exit(0);
        return null;
    }
}
