package commands;

import collection.CollectionManager;
import collection.Dragon;
import utils.CommandLine;
import utils.ElementReadMode;

import java.util.ArrayList;

public class AddIfMax extends Command{
    public AddIfMax() {
        super("add_if_max {element} : добавить новый элемент в коллекцию, если его значение" +
                " превышает значение наибольшего элемента этой коллекции", "add_if_max", 0);
    }

    @Override
    public void execute() {
        if(CollectionManager.getCollectionSize() > 0){
            CommandLine.outLn("Введите элемент для сравнения:");
            CommandLine.outLn("Для ввода null используйте пустую строчку.");
            CommandLine.setElementMode(ElementReadMode.ELEMENT_COMPARE);
            Add.addInit();
        }else{
            CommandLine.errorOut("Коллекция пуста! Воспользуйтесь командой <add> для добавления элементов!");
        }
    }

    public static void compare(ArrayList<Object> fields){
        Dragon dragon = CollectionManager.getMaxElement();
        Dragon newDragon = CollectionManager.createNewDragon(fields);

        if (dragon.compareTo(newDragon) < 0){
            CollectionManager.addDragon(newDragon);
            CommandLine.successOut("Дракон добавлен в коллекцию!");
        } else{
            CommandLine.errorOut("Дракон не добавлен в коллекцию!");
        }

    }


}
