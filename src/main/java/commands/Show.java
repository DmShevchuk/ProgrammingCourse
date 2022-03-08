package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Show extends Command{
    public Show() {
        super("show : display all elements of the collection in string representation", "show", 0);
    }

    @Override
    public void execute() {
        CommandLine.outLn(CollectionManager.collectionToString());
    }
}
