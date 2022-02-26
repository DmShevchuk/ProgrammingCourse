package commands;

import collection.CollectionManager;
import collection.DragonHead;
import utils.CommandLine;

public class RemoveAllByHead extends Command {

    public RemoveAllByHead() {
        super("remove_all_by_head head : удалить из коллекции все элементы," +
                " значение поля head которого эквивалентно заданному", "remove_all_by_head", 1);
    }

    @Override
    public void execute() {
        if (CollectionManager.getCollectionSize() > 0) {
            try {
                DragonHead head = new DragonHead((long) Long.parseLong(CommandManager.getARG()));
                CollectionManager.removeByHead(head);
                CommandLine.outLn(String.format("В коллекции не осталось драконов с размером головы = %s.",
                        CommandManager.getARG()));
            } catch (ClassCastException e) {
                CommandLine.outLn(String.format("Невозможно привести элемент %s %s к Long -> DragonHead.",
                        CommandManager.getARG().getClass(), CommandManager.getARG()));
            }
            return;
        }
        CommandLine.outLn("Сейчас коллекция пуста, нам нечего удалять!");
    }
}
