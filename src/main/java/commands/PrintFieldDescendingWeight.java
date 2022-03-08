package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class PrintFieldDescendingWeight extends Command{
    public PrintFieldDescendingWeight() {
        super("print_field_descending_weight : display the values of the weight field of all elements",
                "print_field_descending_weight", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() != 0){
            CommandLine.outLn(CollectionManager.sortByWeight());
            return;
        }
        CommandLine.errorOut("Populate a collection with items to sort by weight!");
    }
}
