package commands;

import collection.CollectionManager;
import exceptions.AddingRepeatedCommandException;
import utils.CommandLine;

public class RemoveByID extends Command implements UsesCollectionManager {
    private final CommandManager commandManager;
    public RemoveByID(CommandLine commandLine, CommandManager commandManager) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        if (commandManager.getARG() != null) {
            try {
                Integer id = Integer.parseInt(commandManager.getARG());
                if (collectionManager.checkExistingID(id)) {
                    collectionManager.deleteElementByID(id);
                    commandLine.successOut(String.format("Dragon with id=%d was successfully removed!", id));
                } else {
                    commandLine.errorOut(String.format("id=%d does not exist!", id));
                }
            } catch (Exception e) {
                commandLine.errorOut(String.format("Cannot cast string <%s> to Integer!", commandManager.getARG()));
            }
        }
    }
}
