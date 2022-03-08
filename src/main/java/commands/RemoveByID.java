package commands;

import collection.CollectionManager;
import exceptions.AddingRepeatedCommandException;
import utils.CommandLine;

public class RemoveByID extends Command {
    public RemoveByID() {
        super("remove_by_id id : remove element from collection by its id", "remove_by_id", 1);
    }

    @Override
    public void execute() {
        if (CommandManager.getARG() != null) {
            try {
                Integer id = Integer.parseInt(CommandManager.getARG());
                if (CollectionManager.checkExistingID(id)) {
                    CollectionManager.deleteElementByID(id);
                    CommandLine.successOut(String.format("Dragon with id=%d was successfully removed!", id));
                } else {
                    CommandLine.errorOut(String.format("id=%d does not exist!", id));
                }
            } catch (Exception e) {
                CommandLine.errorOut(String.format("Cannot cast string <%s> to Integer!", CommandManager.getARG()));
            }
        }
    }
}
