package commands;

import collection.Dragon;
import utils.CommandLine;
import utils.ElementReadMode;


public class AddIfMax extends Command implements UsesCollectionManager {
    private final CommandManager commandManager;
    public AddIfMax(CommandLine commandLine, CommandManager commandManager) {
        super("add_if_max", "|| add a new element to the collection if its value" +
                " exceeds the value of the largest element of this collection", 0, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        if (collectionManager.getCollectionSize() > 0) {
            commandLine.outLn("Enter an item to compare:");
            commandLine.outLn("(Empty string -> null)");
            commandLine.setElementMode(ElementReadMode.ELEMENT_COMPARE);

            ((Add)commandManager.getCommand("add")).addInit();
        } else {
            commandLine.errorOut("The collection is empty! Use the <add> command to add elements!");
        }
    }

    public void compare(Dragon.Builder newDragon) {
        Dragon dragon = collectionManager.getMaxElement();

        if (dragon.compareTo(newDragon.build()) < 0) {
            collectionManager.addDragon(newDragon);
            commandLine.successOut("Dragon added to collection!");
        } else {
            commandLine.errorOut("The dragon is not added to the collection!");
        }

    }


}
