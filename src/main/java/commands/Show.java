package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Show extends Command implements UsesCollectionManager {
    public Show(CommandLine commandLine) {
        super("show", "|| display all elements of the collection in string representation", 0, commandLine);
    }

    @Override
    public void execute() {
        commandLine.outLn(collectionManager.collectionToString());
    }
}
