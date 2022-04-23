package commands.command;

import commands.Command;
import commands.CommandManager;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class RemoveByID extends Command {
    private final CommandManager commandManager;
    public RemoveByID(CommandLine commandLine, CommandManager commandManager) {
        super("remove_by_id",
                "||{id}  remove element from collection by its id", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        try {
            Integer.parseInt(commandManager.getARG());
            client.send(new Request.Builder().setCommandName(this.getName()).setArgs(commandManager.getARG()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            commandLine.errorOut("Невозможно получить доступ к серверу, повторите попытку позже!");
            commandLine.showOfflineCommands();
            client.resetSocketChannel();
        }
    }
}
