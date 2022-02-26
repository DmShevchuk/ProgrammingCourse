package commands;

import exceptions.AddingRepeatedCommandException;
import utils.CommandLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class CommandManager {
    private static HashMap<String, Command> commandHashMap = new HashMap<>();
    private static Stack<String> STACK = new Stack<>();
    private static String ARG = null;

    public static void addCommand(Command command) throws AddingRepeatedCommandException {
        if (!commandHashMap.containsKey(command.getName())) {
            commandHashMap.put(command.getName(), command);
        } else {
            throw new AddingRepeatedCommandException(String.format("Команда %s уже добавлена!", command.getName()));
        }
    }

    public static boolean findCommand(String cmd) {
        if (commandHashMap.containsKey(cmd)) {
            return true;
        }
        return false;
    }

    public static boolean checkCommand(String cmd, int argsSize) {
        if (!commandHashMap.containsKey(cmd)) {
            CommandLine.outLn(String.format("Не удалось распознать команду <%s>!", cmd));
            return false;
        }
        if ((argsSize - 1) != commandHashMap.get(cmd).getArgQuantity()) {
            CommandLine.outLn(String.format("Команда <%s> принимает аргументов: %d, подано аргументов: %d!",
                    cmd, commandHashMap.get(cmd).getArgQuantity(), argsSize - 1));
            return false;
        }
        return true;
    }

    public static void runCommand(String cmd) {
        try {
            commandHashMap.get(cmd).execute();
            STACK.push(cmd);
            if (STACK.size() == 11) {
                STACK.remove(0);
            }
        } catch (Exception e) {
            CommandLine.outLn(String.format("Не удалось запустить команду <%s>! (См. help)", cmd));
        } finally {
            resetArg();
        }
    }

    public static String getARG() {
        return ARG;
    }

    public static void setARG(String arg) {
        ARG = arg;
    }

    public static void resetArg() {
        ARG = null;
    }

    public static Stack<String> getStack() {
        return (Stack<String>) STACK.clone();
    }

    public static ArrayList<String> getCommandsInfo() {
        ArrayList<String> lst = new ArrayList<>();
        for (Command cmd : commandHashMap.values()) {
            lst.add(cmd.getInfo());
        }
        return lst;
    }
}
