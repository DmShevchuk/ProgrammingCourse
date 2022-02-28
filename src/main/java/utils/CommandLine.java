package utils;

import commands.*;
import exceptions.AddingRepeatedCommandException;

import java.util.Scanner;

public class CommandLine {
    private static String USER_INPUT_PREFIX = ">>";
    private static final Scanner SCANNER = new Scanner(System.in);

    private static InputMode INPUT_MODE = InputMode.COMMAND;

    public void run() throws AddingRepeatedCommandException, NoSuchFieldException {
        String INPUT_COMMAND;
        CommandManager.addCommand(new Help());
        CommandManager.addCommand(new Show());
        CommandManager.addCommand(new Info());
        CommandManager.addCommand(new RemoveByID());
        CommandManager.addCommand(new Clear());
        CommandManager.addCommand(new History());
        CommandManager.addCommand(new MinByID());
        CommandManager.addCommand(new RemoveFirst());
        CommandManager.addCommand(new PrintFieldDescendingWeight());
        CommandManager.addCommand(new RemoveAllByHead());
        CommandManager.addCommand(new Exit());
        CommandManager.addCommand(new Save());
        CommandManager.addCommand(new Add());
        CommandManager.addCommand(new UpdateId());
        CommandManager.addCommand(new AddIfMax());

        while (true) {
            // Выводим в консоль >> для ввода пользователя
            out(USER_INPUT_PREFIX);

            INPUT_COMMAND = SCANNER.nextLine().strip();

            if (INPUT_MODE == InputMode.COMMAND) {
                if (INPUT_COMMAND.length() == 0) {
                    outLn("Введена пустая строка");
                    continue;
                }

                String[] userCommand = INPUT_COMMAND.split("\\s");

                if (CommandManager.checkCommand(userCommand[0], userCommand.length)) {
                    if (userCommand.length == 2) {
                        CommandManager.setARG(userCommand[1]);
                    }
                    CommandManager.runCommand(userCommand[0]);
                }

            } else if (INPUT_MODE == InputMode.ELEMENT_ADD || INPUT_MODE == InputMode.ELEMENT_UPDATE ||
                    INPUT_MODE == InputMode.ELEMENT_COMPARE) {
                Add.addValue(INPUT_COMMAND);
            }
        }

    }

    public static void setInputMode(InputMode mode) {
        INPUT_MODE = mode;
    }

    public static InputMode getInputMode(){
        return INPUT_MODE;
    }

    public static void setUserInputPrefix(String prefix) {
        USER_INPUT_PREFIX = prefix;
    }

    public static void outLn(String text) {
        System.out.println(text);
    }

    public static void out(String text) {
        System.out.print(text);
    }
//TODO цветной ввод
}