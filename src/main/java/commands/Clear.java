package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Clear extends Command {
    public Clear() {
        super("clear : clear the collection", "clear", 0);
    }

    @Override
    public void execute() {
        CommandLine.outLn(CollectionManager.clearCollection());
    }
}
