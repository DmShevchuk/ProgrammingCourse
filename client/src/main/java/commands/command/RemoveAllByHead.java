package commands.command;

import collection.DragonHead;
import commands.*;
import interaction.Request;
import run.Client;
import run.ResponseReceiver;
import utils.CommandLine;

import java.io.IOException;

public class RemoveAllByHead extends Command {
    private final CommandManager commandManager;

    public RemoveAllByHead(CommandLine commandLine, CommandManager commandManager) {
        super("remove_all_by_head",
                "||{head}  remove all elements from the collection," +
                        " whose head field value is equivalent to the given one", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        try {
            new DragonHead(Long.parseLong(commandManager.getARG()));
            client.send(new Request.Builder().setCommandName(this.getName()).setArgs(commandManager.getARG()).build());
            new ResponseReceiver().getResponse(client, commandLine);
        } catch (ClassCastException | IOException e) {
            commandLine.errorOut("Невозможно получить доступ к серверу, повторите попытку позже!");
            commandLine.showOfflineCommands();
            client.resetSocketChannel();
        }
    }
}

