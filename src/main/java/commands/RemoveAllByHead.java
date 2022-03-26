package commands;

import collection.CollectionManager;
import collection.DragonHead;
import utils.CommandLine;

public class RemoveAllByHead extends Command implements UsesCollectionManager {
    private final CommandManager commandManager;

    public RemoveAllByHead(CommandLine commandLine, CommandManager commandManager) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        if (collectionManager.getCollectionSize() > 0) {
            try {
                DragonHead head = new DragonHead((long) Long.parseLong(commandManager.getARG()));
                collectionManager.removeByHead(head);
                commandLine.successOut(String.format("There are no dragons with a head size left in the" +
                                " collection = %s.",
                        commandManager.getARG()));
            } catch (ClassCastException e) {
                commandLine.errorOut(String.format("Cannot cast element %s %s to Long -> DragonHead.",
                        commandManager.getARG().getClass(), commandManager.getARG()));
            }
            return;
        }
        commandLine.errorOut("Now the collection is empty, we have nothing to delete!");
    }
}
