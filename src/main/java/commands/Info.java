package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Info extends Command implements UsesCollectionManager {
    public Info(CommandLine commandLine) {
        super("info",
                "|| display information about the collection (type, initialization date, number of elements)",
                0, commandLine);
    }

    @Override
    public void execute() {
        commandLine.outLn(collectionManager.getInfo());
    }

}
