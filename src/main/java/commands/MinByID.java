package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class MinByID extends Command implements UsesCollectionManager {
    public MinByID(CommandLine commandLine) {
        super("min_by_id",
                "|| display any object from the collection whose id field value is the minimum",
                0, commandLine);
    }

    @Override
    public void execute() {
        Integer minID = collectionManager.getMinID();

        if (minID.equals(Integer.MAX_VALUE)) {
            commandLine.errorOut("Collection is empty!");
            return;
        }

        commandLine.outLn(collectionManager.getElementByID(minID).toString());
    }
}
