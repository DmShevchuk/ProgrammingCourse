package commands;

import collection.CollectionManager;
import collection.DragonHead;
import utils.CommandLine;

public class RemoveAllByHead extends Command {

    public RemoveAllByHead() {
        super("remove_all_by_head head : remove all elements from the collection," +
                " whose head field value is equivalent to the given one", "remove_all_by_head", 1);
    }

    @Override
    public void execute() {
        if (CollectionManager.getCollectionSize() > 0) {
            try {
                DragonHead head = new DragonHead((long) Long.parseLong(CommandManager.getARG()));
                CollectionManager.removeByHead(head);
                CommandLine.successOut(String.format("There are no dragons with a head size left in the" +
                                " collection = %s.",
                        CommandManager.getARG()));
            } catch (ClassCastException e) {
                CommandLine.errorOut(String.format("Cannot cast element %s %s to Long -> DragonHead.",
                        CommandManager.getARG().getClass(), CommandManager.getARG()));
            }
            return;
        }
        CommandLine.errorOut("Now the collection is empty, we have nothing to delete!");
    }
}
