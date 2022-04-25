package commands.command;

import commands.Command;
import run.Client;
import utils.CommandLine;

public class Exit extends Command {
    public Exit(CommandLine commandLine) {
        super("exit", "|| terminate program (without saving to file)", 0, commandLine);
    }

    @Override
    public void execute(Client client) {
        client.resetSocketChannel();
        System.exit(0);
    }
}
