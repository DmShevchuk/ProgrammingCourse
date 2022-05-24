package commands.command;

import commands.Command;
import commands.CommandManager;

import java.util.Stack;

public class History extends Command {
    private final CommandManager commandManager;

    public History(CommandManager commandManager) {
        super("history",
                "|| print the last 10 commands (without their arguments)", 0);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        Stack<String> commands = commandManager.getStack();
        int size = commands.size();

        StringBuilder toReturn = new StringBuilder(String.format("Latest commands (%d):\n", size));
        for (String cmd : commands) {
            toReturn.append(cmd).append("\n");
        }
        //commandLine.errorOut(size == 0 ? "Command list is empty!" : toReturn.toString().strip());
    }
}
