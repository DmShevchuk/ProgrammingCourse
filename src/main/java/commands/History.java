package commands;

import utils.CommandLine;

import java.util.Stack;

public class History extends Command {
    public History() {
        super("history : вывести последние 10 команд (без их аргументов)", "history", 0);
    }

    @Override
    public void execute() {
        Stack<String> commands = CommandManager.getStack();
        int size = commands.size();

        String toReturn = String.format("Последние команды (%d):\n", size);

        for (String cmd : commands) {
            toReturn += cmd + "\n";
        }

        CommandLine.errorOut(size == 0 ? "Список команд пуст!" : toReturn.strip());
    }
}
