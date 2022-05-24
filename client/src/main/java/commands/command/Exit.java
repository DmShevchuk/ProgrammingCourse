package commands.command;

import commands.Command;
import account.Client;

public class Exit extends Command {
    private final Client client;

    public Exit(Client client) {
        super("exit", "|| terminate program (without saving to file)", 0);
        this.client = client;
    }

    @Override
    public void execute() {
        client.refuseConnection();
        System.exit(0);
    }
}
