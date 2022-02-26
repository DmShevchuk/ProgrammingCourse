package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class RemoveFirst extends Command{
    public RemoveFirst() {
        super("remove_first : удалить первый элемент из коллекции",
                "remove_first", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() > 0){
            CollectionManager.removeFirst();
            CommandLine.outLn("Первый элемент коллекции успешно удалён!");
            return;
        }
        CommandLine.outLn("В коллекции нет элементов, удалять нечего!");
    }
}
