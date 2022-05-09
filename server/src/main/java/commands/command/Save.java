package commands.command;

import collection.*;
import commands.Command;
import interaction.*;

import java.util.logging.Logger;

public class Save extends Command {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager, Logger logger) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return null;
    }
}
