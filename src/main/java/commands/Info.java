package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Info extends Command{
    public Info(){
        super("info : display information about the collection (type, initialization date, number of elements)",
                "info", 0);
    }

    @Override
    public void execute() {
        CommandLine.outLn(CollectionManager.getInfo());
    }

}
