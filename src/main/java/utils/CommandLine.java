package utils;

import commands.*;
import exceptions.AddingRepeatedCommandException;

import java.util.Scanner;

public class CommandLine {
    private final static String USER_COMMAND_PREFIX = ">>";
    private static final Scanner SCANNER = new Scanner(System.in);

    private static InputMode COMMAND_INPUT = InputMode.COMMAND;

    public void run() throws AddingRepeatedCommandException {
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

        do {
            // Выводим в консоль >> для ввода пользователя
            System.out.print(USER_COMMAND_PREFIX);

            INPUT_COMMAND = SCANNER.nextLine().strip();

            if (INPUT_COMMAND.length() == 0 && COMMAND_INPUT == InputMode.COMMAND) {
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

        } while (!INPUT_COMMAND.equals("exit"));

    }

    public static void outLn(String text) {
        System.out.println(text);
    }
//TODO цветной ввод
}