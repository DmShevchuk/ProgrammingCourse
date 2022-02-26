package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Info extends Command{
    public Info(){
        super("info : вывести информацию о коллекции (тип, дата инициализации, количество элементов)", "info", 0);
    }

    @Override
    public void execute() {
        CommandLine.outLn(CollectionManager.getInfo());
    }

}
