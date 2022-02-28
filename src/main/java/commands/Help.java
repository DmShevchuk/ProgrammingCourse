package commands;
import utils.CommandLine;

import java.util.ArrayList;

public class Help extends Command {

    public Help() {
        super("help : вывод информации по всем доступным командам", "help", 0);

    }

    @Override
    public void execute() {
        String commandsInfo = "";
        ArrayList<String> lst = CommandManager.getCommandsInfo();
        CommandLine.outLn(String.join("\n", lst));
    }

}
