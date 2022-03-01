package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class MinByID extends Command {
    public MinByID() {
        super("min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным",
                "min_by_id", 0);
    }

    @Override
    public void execute() {
        Integer minID = CollectionManager.getMinID();

        if(minID.equals(Integer.MAX_VALUE)){
            CommandLine.errorOut("Коллекция пуста!");
            return;
        }

        CommandLine.outLn(CollectionManager.getElementByID(minID).toString());
    }
}
