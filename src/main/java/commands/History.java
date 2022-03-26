package commands;

import utils.CommandLine;

import java.util.Stack;

public class History extends Command {
    private final CommandManager commandManager;
    public History(CommandLine commandLine, CommandManager commandManager) {
        super("history",
                "|| print the last 10 commands (without their arguments)", 0, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        Stack<String> commands = commandManager.getStack();
        int size = commands.size();

        String toReturn = String.format("Latest commands (%d):\n", size);

        for (String cmd : commands) {
            toReturn += cmd + "\n";
        }

        commandLine.errorOut(size == 0 ? "Command list is empty!" : toReturn.strip());
    }
}
