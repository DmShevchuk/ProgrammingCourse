package commands;

import exceptions.IncorrectArgQuantityException;
import exceptions.IncorrectCommandException;
import run.Client;
import utils.CommandLine;

import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

public class CommandManager {
    private final HashMap<String, Command> commandHashMap = new HashMap<>();
    private final Stack<String> stack = new Stack<>();
    private static String arg = null;
    private final CommandLine commandLine;
    private final Client client;

    public CommandManager(CommandLine commandLine, Client client) {
        this.commandLine = commandLine;
        this.client = client;
    }

    public void recognizeCommand(String cmd, int argsSize) {
        if (!commandHashMap.containsKey(cmd)) {
            throw new IncorrectCommandException(String.format("Failed to recognize command <%s>!", cmd));
        }
        if (argsSize != commandHashMap.get(cmd).getArgQuantity()) {
            throw new IncorrectArgQuantityException(String.format("Command <%s> takes arguments: %d, arguments supplied: %d!",
                    cmd, commandHashMap.get(cmd).getArgQuantity(), argsSize));
        }
    }

    public void addCommand(Command command) {
        if (!commandHashMap.containsKey(command.getName())) {
            commandHashMap.put(command.getName(), command);
        }
    }

    public Command getCommand(String cmd) {
        if (commandHashMap.containsKey(cmd)) {
            return commandHashMap.get(cmd);
        }
        return null;
    }

    public void runCommand(String cmd) {
        try {
            commandHashMap.get(cmd).execute(client);
            //Запись команды в историю
            stack.push(cmd);
            if (stack.size() == 11) {
                stack.remove(0);
            }

        } catch (Exception e) {
            commandLine.errorOut(String.format("Failed to run command <%s>! (See help)", cmd));
        } finally {
            resetArg();
        }
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void resetArg() {
        arg = null;
    }

    public Stack<String> getStack() {
        return (Stack<String>) stack.clone();
    }

    public TreeMap<String, String> getCommandsInfo() {
        TreeMap<String, String> lst = new TreeMap<>();
        for (Command cmd : commandHashMap.values()) {
            lst.put(cmd.getName(), cmd.getInfo());
        }
        return lst;
    }
}
