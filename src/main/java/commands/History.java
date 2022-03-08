package commands;

import utils.CommandLine;

import java.util.Stack;

public class History extends Command {
    public History() {
        super("history : print the last 10 commands (without their arguments)", "history", 0);
    }

    @Override
    public void execute() {
        Stack<String> commands = CommandManager.getStack();
        int size = commands.size();

        String toReturn = String.format("Latest commands (%d):\n", size);

        for (String cmd : commands) {
            toReturn += cmd + "\n";
        }

        CommandLine.errorOut(size == 0 ? "Command list is empty!" : toReturn.strip());
    }
}
