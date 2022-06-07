package commands.command;

import commands.Command;
import commands.CommandFactory;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class Help extends Command {
    private final CommandFactory commandFactory;

    public Help(CommandFactory commandFactory) {
        super("help", "|| displaying information on all available commands", 0);
        this.commandFactory = commandFactory;
    }


    @Override
    public <T> Response execute(T args) throws IOException {
        // Получение HasMap<Команда, Описание>
        Map<String, String> commandsInfo = commandFactory.getCommandsInfo();

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
        return new Response(ResponseStatus.SUCCESS, String.valueOf(string));
    }
}
