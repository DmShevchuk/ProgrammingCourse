package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class RemoveFirst extends Command{
    public RemoveFirst() {
        super("remove_first : remove the first element from the collection",
                "remove_first", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() > 0){
            CollectionManager.removeFirst();
            CommandLine.successOut("The first element of the collection was successfully removed!");
            return;
        }
        CommandLine.errorOut("There are no elements in the collection, there is nothing to remove!");
    }
}
