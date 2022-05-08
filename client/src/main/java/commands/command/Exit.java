package commands.command;

import commands.Command;
import run.Client;
import utils.CommandLine;

public class Exit extends Command {
    private final Client client;

    public Exit(CommandLine commandLine, Client client) {
        super("exit", "|| terminate program (without saving to file)", 0, commandLine);
        this.client = client;
    }

    @Override
    public void execute() {
        client.resetSocketChannel();
        System.exit(0);
    }
}
