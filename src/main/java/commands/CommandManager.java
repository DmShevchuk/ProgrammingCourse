package commands;

import exceptions.AddingRepeatedCommandException;
import utils.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class CommandManager {
    private static HashMap<String, Command> commandHashMap = new HashMap<>();
    private static Stack<String> STACK = new Stack<>();
    private static String ARG = null;
    private final CommandLine commandLine;

    public CommandManager(CommandLine commandLine){
        this.commandLine = commandLine;
    }

    public void addCommand(Command command) throws AddingRepeatedCommandException {
        if (!commandHashMap.containsKey(command.getName())) {
            commandHashMap.put(command.getName(), command);
        } else {
            throw new AddingRepeatedCommandException(String.format("Command %s already added!", command.getName()));
        }
    }

    public Command getCommand(String cmd) {
        if (commandHashMap.containsKey(cmd)) {
            return commandHashMap.get(cmd);
        }
        return null;
    }

    public boolean checkCommand(String cmd, int argsSize) {
        if (!commandHashMap.containsKey(cmd)) {
            commandLine.errorOut(String.format("Failed to recognize command <%s>!", cmd));
            return false;
        }
        if ((argsSize - 1) != commandHashMap.get(cmd).getArgQuantity()) {
            commandLine.errorOut(String.format("Command <%s> takes arguments: %d, arguments supplied: %d!",
                    cmd, commandHashMap.get(cmd).getArgQuantity(), argsSize - 1));
            return false;
        }
        return true;
    }

    public void runCommand(String cmd) {
        try {
            commandHashMap.get(cmd).execute();

            //Запись команды в историю
            STACK.push(cmd);
            if (STACK.size() == 11) {
                STACK.remove(0);
            }

        } catch (Exception e) {
            commandLine.errorOut(String.format("Failed to run command <%s>! (See help)", cmd));
        } finally {
            resetArg();
        }
    }

    public String getARG() {
        return ARG;
    }

    public void setARG(String arg) {
        ARG = arg;
    }

    public void resetArg() {
        ARG = null;
    }

    public Stack<String> getStack() {
        return (Stack<String>) STACK.clone();
    }

    public TreeMap<String, String> getCommandsInfo() {
        TreeMap<String, String> lst = new TreeMap();
        for (Command cmd : commandHashMap.values()) {
            lst.put(cmd.getName(), cmd.getInfo());
        }
        return lst;
    }
}
