package commands;

import collection.CollectionManager;
import collection.Dragon;
import utils.CommandLine;
import utils.ElementReadMode;

import java.util.ArrayList;

public class AddIfMax extends Command{
    public AddIfMax() {
        super("add_if_max {element} : add a new element to the collection if its value" +
                " exceeds the value of the largest element of this collection", "add_if_max", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() > 0){
            CommandLine.outLn("Enter an item to compare:");
            CommandLine.outLn("To enter null, use an empty string.");
            CommandLine.setElementMode(ElementReadMode.ELEMENT_COMPARE);
            Add.addInit();
        }else{
            CommandLine.errorOut("The collection is empty! Use the <add> command to add elements!");
        }
    }

    public static void compare(ArrayList<Object> fields){
        Dragon dragon = CollectionManager.getMaxElement();
        Dragon newDragon = CollectionManager.createNewDragon(fields);

        if (dragon.compareTo(newDragon) < 0){
            CollectionManager.addDragon(newDragon);
            CommandLine.successOut("Dragon added to collection!");
        } else{
            CommandLine.errorOut("The dragon is not added to the collection!");
        }

    }


}
