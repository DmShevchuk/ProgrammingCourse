package commands;

import utils.CommandLine;

public class Clear extends Command implements UsesCollectionManager {
    public Clear(CommandLine commandLine) {
        super("clear", "|| clear the collection", 0, commandLine);
    }

    @Override
    public void execute() {
        commandLine.successOut(collectionManager.clearCollection());
    }
}
