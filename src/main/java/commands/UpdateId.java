package commands;

import collection.CollectionManager;
import utils.CommandLine;
import utils.ElementReadMode;

import java.util.ArrayList;

public class UpdateId extends Command {
    private static Integer currentId;

    public UpdateId() {
        super("update id {element} : update the value of the collection element whose id is equal to the given one",
                "update", 1);
    }

    @Override
    public void execute() {
        try {
            currentId = Integer.parseInt(CommandManager.getARG());
            if (CollectionManager.checkExistingID(currentId)) {
                CommandLine.outLn("Updating a collection item:\n"
                        + CollectionManager.getElementByID(currentId).toString());
                CommandLine.outLn("To enter null, use an empty string.");
                CommandLine.setElementMode(ElementReadMode.ELEMENT_UPDATE);
                Add.addInit();
            }
        } catch (ClassCastException e) {
            CommandLine.errorOut("Impossible to get id=" + CommandManager.getARG());
        }
    }

    public static void update(ArrayList<Object> fields) {
        try {
            CollectionManager.updateElementById(currentId, fields);
            CommandLine.successOut(String.format("Collection element with id=%d was successfully updated!", currentId));
            currentId = null;
        } catch (Exception e) {
            CommandLine.errorOut("Failed to update element value!");
        }
    }
}
