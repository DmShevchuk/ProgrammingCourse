package commands.command;

import commands.Command;
import commands.CommandManager;

import java.util.*;

public class Help extends Command {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "|| displaying information on all available commands", 0);
        this.commandManager = commandManager;

    }

    @Override
    public void execute() {
        // Получение HasMap<Команда, Описание>
        Map<String, String> commandsInfo = commandManager.getCommandsInfo();

        // Получение названия самой длинной команды
        Optional<String> maxLength = commandsInfo.keySet().stream().max(Comparator.comparing(String::length));

        // Количество пробелов между названием команды и её описанием
        int spacesBetweenWords = maxLength.get().length() + 5;

        StringBuilder string = new StringBuilder();
        for (String key : commandsInfo.keySet()) {
            // Указание количества пробелов между названием команды и её описанием
            String settings = "%-" + spacesBetweenWords + "s";

            string.append(String.format(settings, key));
            string.append(commandsInfo.get(key)).append("\n");
        }
        //commandLine.outLn(String.valueOf(string).strip());
    }

}
