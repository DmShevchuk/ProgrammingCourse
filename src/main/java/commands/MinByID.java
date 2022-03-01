package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class MinByID extends Command {
    public MinByID() {
        super("min_by_id : display any object from the collection whose id field value is the minimum",
                "min_by_id", 0);
    }

    @Override
    public void execute() {
        Integer minID = CollectionManager.getMinID();

        if(minID.equals(Integer.MAX_VALUE)){
            CommandLine.errorOut("Collection is empty!");
            return;
        }

        CommandLine.outLn(CollectionManager.getElementByID(minID).toString());
    }
}
