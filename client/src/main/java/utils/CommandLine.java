package utils;

import commands.CommandManager;
import commands.command.*;
import exceptions.IncorrectArgQuantityException;
import exceptions.IncorrectCommandException;
import run.Client;
import run.ServerErrorHandler;
import java.util.ArrayList;
import java.util.Scanner;


public class CommandLine {
    private String USER_INPUT_PREFIX = ">>";
    private final Scanner scanner = new Scanner(System.in);
    private InputSource INPUT_SOURCE = InputSource.COMMAND;
    private CommandManager commandManager;
    private ArrayList<String> scriptInstructions = new ArrayList<>();

    public void run(Client client) {
        String INPUT_COMMAND;
        commandManager = new CommandManager(this, client);
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
            INPUT_COMMAND = getNextLine();
            parseInputLine(INPUT_COMMAND);
        }

    }

    public String getNextLine() {
        if (INPUT_SOURCE == InputSource.COMMAND) {
            return scanner.nextLine().trim();
        }
        return getNextScriptInstruction();
    }

    public void parseInputLine(String line) {
        String[] userCommand = line.split("\\s");
        try {
            commandManager.recognizeCommand(userCommand[0], userCommand.length - 1);
            if (userCommand.length == 2) {
                commandManager.setArg(userCommand[1]);
            }
            commandManager.runCommand(userCommand[0]);
        } catch (IncorrectCommandException | IncorrectArgQuantityException e) {
            errorOut(e.getMessage());
        }
    }

    public void addScriptInstructions(ArrayList<String> lines) {
        scriptInstructions.addAll(lines);
    }

    public String getNextScriptInstruction() {
        String instruction = scriptInstructions.get(0);
        outLn(instruction);
        scriptInstructions.remove(0);
        if (scriptInstructions.size() == 0) {
            setInputSource(InputSource.COMMAND);
            ((ExecuteScript)commandManager.getCommand("execute_script")).clearFields();
        }
        return instruction;
    }

    public void setInputSource(InputSource mode) {
        INPUT_SOURCE = mode;
    }

    public void setUserInputPrefix(String prefix) {
        USER_INPUT_PREFIX = prefix;
    }

    public void showOfflineCommands() {
        outLn("""
                Available offline-commands:
                execute_script                    ||{file_name} read and execute a script from the specified file
                exit                              || terminate program (without saving to file)
                help                              || displaying information on all available commands
                history                           || print the last 10 commands (without their arguments)""");
    }

    public void out(String text) {
        System.out.print(text);
    }

    public void outLn(String text) {
        System.out.println(text);
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
