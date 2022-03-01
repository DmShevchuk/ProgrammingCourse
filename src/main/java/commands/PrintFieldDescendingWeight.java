package commands;

import collection.CollectionManager;
import utils.CommandLine;

public class PrintFieldDescendingWeight extends Command{
    public PrintFieldDescendingWeight() {
        super("print_field_descending_weight : вывести значения поля weight всех элементов в порядке убывания",
                "print_field_descending_weight", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() != 0){
            CommandLine.outLn(CollectionManager.sortByWeight());
            return;
        }
        CommandLine.errorOut("Заполните коллекцию элементами для сортировки по весу!");
    }
}
