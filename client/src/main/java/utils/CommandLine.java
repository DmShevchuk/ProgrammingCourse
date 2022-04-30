package utils;

import commands.*;
import commands.command.*;
import run.Client;
import run.ServerErrorHandler;

import java.util.Scanner;

public class CommandLine {
    private String USER_INPUT_PREFIX = ">>";
    private final Scanner SCANNER = new Scanner(System.in);
    private InputSource INPUT_SOURCE = InputSource.COMMAND;
    private ElementReadMode ELEMENT_MODE = ElementReadMode.STANDARD;

    public void run(Client client) {
        String INPUT_COMMAND;
        CommandManager commandManager = new CommandManager(this, client);
        ServerErrorHandler errorHandler = new ServerErrorHandler(client, this);

        commandManager.addCommand(new Help(this, commandManager));
        commandManager.addCommand(new Show(this, errorHandler));
        commandManager.addCommand(new Info(this, errorHandler));
        commandManager.addCommand(new RemoveByID(this, commandManager, errorHandler));
        commandManager.addCommand(new Clear(this, errorHandler));
        commandManager.addCommand(new History(this, commandManager));
        commandManager.addCommand(new MinByID(this, errorHandler));
        commandManager.addCommand(new RemoveFirst(this, errorHandler));
        commandManager.addCommand(new PrintFieldDescendingWeight(this, errorHandler));
        commandManager.addCommand(new RemoveAllByHead(this, commandManager, errorHandler));
        commandManager.addCommand(new Exit(this));
        commandManager.addCommand(new Add(this, commandManager, errorHandler));
        commandManager.addCommand(new UpdateId(this, commandManager, errorHandler));
        commandManager.addCommand(new AddIfMax(this, commandManager, errorHandler));
        commandManager.addCommand(new ExecuteScript(this, commandManager));

        while (true) {
            // Выводим в консоль >> для ввода пользователя
            out(USER_INPUT_PREFIX);

            if (INPUT_SOURCE == InputSource.SCRIPT) {
                INPUT_COMMAND = ((ExecuteScript) commandManager.getCommand("execute_script")).nextLine();
            } else {
                INPUT_COMMAND = SCANNER.nextLine().strip();
            }

            if (INPUT_SOURCE == InputSource.SCRIPT) outLn(INPUT_COMMAND);

            if (ELEMENT_MODE == ElementReadMode.STANDARD) {
                if (INPUT_COMMAND.length() == 0) {
                    outLn("Empty string entered");
                    continue;
                } else if (INPUT_COMMAND.equals("__stopScript__")) {
                    continue;
                }

                String[] userCommand = INPUT_COMMAND.split("\\s");

                if (commandManager.checkCommand(userCommand[0], userCommand.length)) {
                    if (userCommand.length == 2) {
                        commandManager.setARG(userCommand[1]);
                    }
                    commandManager.runCommand(userCommand[0]);
                }

            } else {
                ((Add) commandManager.getCommand("add")).addValue(INPUT_COMMAND);
            }
        }

    }

    public void setInputSource(InputSource mode) {
        INPUT_SOURCE = mode;
    }

    public void setElementMode(ElementReadMode mode) {
        ELEMENT_MODE = mode;
    }

    public ElementReadMode getElementMode() {
        return ELEMENT_MODE;
    }

    public void setUserInputPrefix(String prefix) {
        USER_INPUT_PREFIX = prefix;
    }

    public void outLn(String text) {
        System.out.println(text);
    }

    public void showOfflineCommands() {
        outLn("""
                Available offline-commands:
                execute_script                    ||{file_name} read and execute a script from the specified file
                exit                              || terminate program (without saving to file)
                help                              || displaying information on all available commands
                history                           || print the last 10 commands (without their arguments)""");
    }

    public static void out(String text) {
        System.out.print(text);
    }

    public void successOut(String text) {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_GREEN + text + ANSI_RESET);
    }

    public void errorOut(String text) {
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_RED + text + ANSI_RESET);
    }
}
