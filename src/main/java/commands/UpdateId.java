package commands;

import collection.Dragon;
import utils.CommandLine;
import utils.ElementReadMode;


public class UpdateId extends Command implements UsesCollectionManager {
    private Integer currentId;
    private CommandManager commandManager;

    public UpdateId(CommandLine commandLine, CommandManager commandManager) {
        super("update",
                "||{id}  update the value of the collection element whose id is equal to the given one",
                1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        try {
            currentId = Integer.parseInt(commandManager.getARG());
            if (collectionManager.checkExistingID(currentId)) {
                commandLine.outLn("Updating a collection item:\n"
                        + collectionManager.getElementByID(currentId).toString());
                commandLine.outLn("To enter null, use an empty string.");
                commandLine.setElementMode(ElementReadMode.ELEMENT_UPDATE);

                ((Add)commandManager.getCommand("add")).addInit();
            }
            else{
                commandLine.errorOut("Не удалось найти значение id=" + commandManager.getARG());
            }
        } catch (ClassCastException e) {
            commandLine.errorOut("Impossible to get id=" + commandManager.getARG());
        }
    }

    public void update(Dragon dragon) {
        try {
            collectionManager.updateElementById(currentId, dragon);
            commandLine.successOut(String.format("Collection element with id=%d was successfully updated!", currentId));
            currentId = null;
        } catch (Exception e) {
            commandLine.errorOut("Failed to update element value!");
        }
    }
}
