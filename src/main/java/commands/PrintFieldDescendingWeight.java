package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class PrintFieldDescendingWeight extends Command implements UsesCollectionManager {
    public PrintFieldDescendingWeight(CommandLine commandLine) {
        super("print_field_descending_weight",
                "|| display the values of the weight field of all elements",
                0, commandLine);
    }

    @Override
    public void execute() {
        if (collectionManager.getCollectionSize() != 0) {
            commandLine.outLn(collectionManager.sortByWeight());
            return;
        }
        commandLine.errorOut("Populate a collection with items to sort by weight!");
    }
}
