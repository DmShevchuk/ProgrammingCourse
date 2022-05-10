package utils;

import commands.CommandManager;
import commands.command.*;
import exceptions.IncorrectArgQuantityException;
import exceptions.IncorrectCommandException;
import interaction.Account;
import run.Client;
import run.ServerErrorHandler;

import java.util.ArrayList;
import java.util.Scanner;


public class CommandLine {
    private final Client client;
    private String userInputPrefix;
    private Scanner scanner;
    private InputSource inputSource;
    private CommandManager commandManager;
    private final ArrayList<String> scriptInstructions = new ArrayList<>();
    private final Account account;

    public CommandLine(Client client) {
        this.client = client;
        this.account = client.getAccount();
        commandLineInit();
    }

    public void run() {
        String INPUT_COMMAND;

        while (true) {
            outPrefix();
            INPUT_COMMAND = getNextLine();
            parseInputLine(INPUT_COMMAND);
        }

    }

    public String getNextLine() {
        if (inputSource == InputSource.COMMAND) {
            return scanner.nextLine().trim();
        }
        return getNextScriptInstruction();
    }

    public void parseInputLine(String line) {
        if (line.length() == 0) {
            return;
        }

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
            commandManager.getExecuteScript().clearFields();
        }
        return instruction;
    }

    public void setInputSource(InputSource mode) {
        inputSource = mode;
    }

    public void setUserInputPrefix(String prefix) {
        userInputPrefix = prefix;
    }

    public void showOfflineCommands() {
        outLn("""
                Available offline-commands:
                execute_script                    ||{file_name} read and execute a script from the specified file
                exit                              || terminate program (without saving to file)
                help                              || displaying information on all available commands
                history                           || print the last 10 commands (without their arguments)""");
    }

    public void commandLineInit() {
        this.userInputPrefix = account.getLogin() + ">";
        this.scanner = new Scanner(System.in);
        this.inputSource = InputSource.COMMAND;

        setUserInputPrefix(userInputPrefix);

        commandManager = new CommandManager(this);
        ServerErrorHandler errorHandler = new ServerErrorHandler(client, this);

        commandManager.addCommand(new Help(this, commandManager));
        commandManager.addCommand(new Show(this, client, errorHandler));
        commandManager.addCommand(new Info(this, client, errorHandler));
        commandManager.addCommand(new RemoveByID(this, client, commandManager, errorHandler));
        commandManager.addCommand(new Clear(this, client, errorHandler));
        commandManager.addCommand(new History(this, commandManager));
        commandManager.addCommand(new MinByID(this, client, errorHandler));
        commandManager.addCommand(new RemoveFirst(this, client, errorHandler));
        commandManager.addCommand(new PrintFieldDescendingWeight(this, client, errorHandler));
        commandManager.addCommand(new RemoveAllByHead(this, client, commandManager, errorHandler));
        commandManager.addCommand(new Exit(this, client));
        commandManager.addCommand(new Add(this, client, errorHandler));
        commandManager.addCommand(new UpdateId(this, client, commandManager, errorHandler));
        commandManager.addCommand(new AddIfMax(this, client, errorHandler));
        commandManager.addCommand(new ExecuteScript(this, commandManager));
        commandManager.addCommand(new Logout(this, client));
    }

    public void out(String text) {
        System.out.print(text);
    }

    public void outPrefix() {
        String ANSI_GREEN = "\u001b[36m";
        String ANSI_RESET = "\u001B[0m";
        System.out.print(ANSI_GREEN + account.getLogin() + ">>" + ANSI_RESET);
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

    public void showOutLn(String ansiCode, String dragonString) {
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ansiCode + dragonString + ANSI_RESET);
    }
}
