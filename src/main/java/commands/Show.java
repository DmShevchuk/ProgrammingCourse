package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class Show extends Command{
    public Show() {
        super("show : вывести все элементы коллекции в строковом представлении", "show", 0);
    }

    @Override
    public void execute() {
        CommandLine.outLn(CollectionManager.collectionToString());
    }
}
