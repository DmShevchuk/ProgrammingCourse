package commands;

import collection.CollectionManager;
import exceptions.AddingRepeatedCommandException;
import utils.CommandLine;

public class RemoveByID extends Command {
    public RemoveByID() {
        super("remove_by_id id : удалить элемент из коллекции по его id", "remove_by_id", 1);
    }

    @Override
    public void execute() {
        if (CommandManager.getARG() != null) {
            try {
                Integer id = Integer.parseInt(CommandManager.getARG());
                if (CollectionManager.checkExistingID(id)) {
                    CollectionManager.deleteElementByID(id);
                    CommandLine.outLn(String.format("Дракон с id=%d успешно удалён!", id));
                } else {
                    CommandLine.outLn(String.format("id=%d не существует!", id));
                }
            } catch (Exception e) {
                CommandLine.outLn(String.format("Невозможно привести строку <%s> к Integer!", CommandManager.getARG()));
            }
        }
    }
}
