package commands;

import commands.command.*;
import exceptions.IncorrectCommandException;
import run.RequestSender;
import run.ResponseReceiver;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final RequestSender sender;
    private final ResponseReceiver receiver;
    private final Map<String, Command> commandHashMap = new HashMap<>();


    public CommandFactory(RequestSender sender, ResponseReceiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
        initCommandHashMap();
    }

    public Command recognizeCommand(String line) {
        String[] instruction = line.split("\\s");
        Command command = getCommand(instruction[0]);
        if (command != null && (instruction.length - 1) == command.getArgQuantity()) {
            return command;
        }
        throw new IncorrectCommandException(String.format("Failed to run command <%s>!", line));
    }

    public Command getCommand(String commandName) {
        return commandHashMap.get(commandName);
    }

    private void initCommandHashMap() {
        commandHashMap.put("update", new UpdateId(sender, receiver));
        commandHashMap.put("add", new Add(sender, receiver));
        commandHashMap.put("remove_by_id", new RemoveByID(sender, receiver));
        commandHashMap.put("add_if_max", new AddIfMax(sender, receiver));
        commandHashMap.put("clear", new Clear(sender, receiver));
        commandHashMap.put("remove_first", new RemoveFirst(sender, receiver));
        commandHashMap.put("remove_all_by_head", new RemoveAllByHead(sender, receiver));
        commandHashMap.put("execute_script", new ExecuteScript(sender, receiver, this));

        commandHashMap.put("help", new Help(this));
        commandHashMap.put("show", new Show(sender, receiver));
        commandHashMap.put("info", new Info(sender, receiver));
        commandHashMap.put("min_by_id", new MinByID(sender, receiver));
        commandHashMap.put("print_field_descending_weight", new PrintFieldDescendingWeight(sender, receiver));
    }

    public Map<String, String> getCommandsInfo() {
        Map<String, String> helpMap = new HashMap<>();
        for (String key : commandHashMap.keySet()) {
            helpMap.put(key, getCommand(key).getInfo());
        }
        return helpMap;
    }
}
