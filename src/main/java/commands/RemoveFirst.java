package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class RemoveFirst extends Command implements UsesCollectionManager {
    public RemoveFirst(CommandLine commandLine) {
        super("remove_first",
                "|| remove the first element from the collection",
                0, commandLine);
    }

    @Override
    public void execute() {
        if (collectionManager.getCollectionSize() > 0) {
            collectionManager.removeFirst();
            commandLine.successOut("The first element of the collection was successfully removed!");
            return;
        }
        commandLine.errorOut("There are no elements in the collection, there is nothing to remove!");
    }
}
