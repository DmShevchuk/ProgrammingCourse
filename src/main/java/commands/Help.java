package commands;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import utils.CommandLine;

import java.util.ArrayList;
import java.util.Set;


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
