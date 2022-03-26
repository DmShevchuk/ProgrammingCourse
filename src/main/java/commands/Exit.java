package commands;

import utils.CommandLine;

public class Exit extends Command {
    public Exit(CommandLine commandLine) {
        super("exit", "|| terminate program (without saving to file)", 0, commandLine);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
