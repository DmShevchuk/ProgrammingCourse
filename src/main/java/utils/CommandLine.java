package utils;

import commands.*;
import exceptions.AddingRepeatedCommandException;

import java.util.Scanner;

public class CommandLine {
    private static String USER_INPUT_PREFIX = ">>";
    private static final Scanner SCANNER = new Scanner(System.in);

    private static InputSource INPUT_SOURCE = InputSource.COMMAND;
    private static ElementReadMode ELEMENT_MODE = ElementReadMode.STANDARD;

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
        CommandManager.addCommand(new ExecuteScript());

        while (true) {
            // Выводим в консоль >> для ввода пользователя
            out(USER_INPUT_PREFIX);

            INPUT_COMMAND = (INPUT_SOURCE == InputSource.COMMAND) ? SCANNER.nextLine().strip() :
                    ExecuteScript.nextLine().strip();

            if (INPUT_SOURCE == InputSource.SCRIPT) {
                outLn(INPUT_COMMAND);
            }

            if (ELEMENT_MODE == ElementReadMode.STANDARD) {
                if (INPUT_COMMAND.length() == 0) {
                    outLn("Введена пустая строка");
                    continue;
                }else if(INPUT_COMMAND.equals("_stop_script_")){
                    continue;
                }

                String[] userCommand = INPUT_COMMAND.split("\\s");

                if (CommandManager.checkCommand(userCommand[0], userCommand.length)) {
                    if (userCommand.length == 2) {
                        CommandManager.setARG(userCommand[1]);
                    }
                    CommandManager.runCommand(userCommand[0]);
                }

            } else {
                Add.addValue(INPUT_COMMAND);
            }
        }

    }

    public static void setInputSource(InputSource mode) {
        INPUT_SOURCE = mode;
    }

    public static void setElementMode(ElementReadMode mode) {
        ELEMENT_MODE = mode;
    }

    public static ElementReadMode getElementMode() {
        return ELEMENT_MODE;
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