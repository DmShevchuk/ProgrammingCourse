package commands;

import collection.CollectionManager;
import utils.CommandLine;
import utils.InputMode;

import java.util.ArrayList;

public class UpdateId extends Command {
    private static Integer currentId;

    public UpdateId() {
        super("update id {element} : обновить значение элемента коллекции, id которого равен заданному",
                "update", 1);
    }

    @Override
    public void execute() {
        try {
            currentId = Integer.parseInt(CommandManager.getARG());
            if (CollectionManager.checkExistingID(currentId)) {
                CommandLine.outLn("Обновление элемента коллекции:\n"
                        + CollectionManager.getElementByID(currentId).toString());
                CommandLine.outLn("Для ввода null используйте пустую строчку.");
                CommandLine.setInputMode(InputMode.ELEMENT_UPDATE);
                Add.addInit();
            }
        } catch (ClassCastException e) {
            CommandLine.outLn("Невозможно получить id=" + CommandManager.getARG());
        }
    }

    public static void update(ArrayList<Object> fields) {
        try {
            CollectionManager.updateElementById(currentId, fields);
            CommandLine.outLn(String.format("Элемент коллекции с id=%d успешно обновлён!", currentId));
            currentId = null;
        } catch (Exception e) {
            CommandLine.outLn("Не удалось обновить значение элемента!");
        }
    }
}
