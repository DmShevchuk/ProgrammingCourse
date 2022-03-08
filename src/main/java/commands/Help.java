package commands;
import utils.CommandLine;

import java.util.ArrayList;
import java.util.Collections;

public class Help extends Command {

    public Help() {
        super("help : displaying information on all available commands", "help", 0);

    }

    @Override
    public void execute() {
        ArrayList<String> lst = CommandManager.getCommandsInfo();
        Collections.sort(lst);
        CommandLine.outLn(String.join("\n", lst));
    }

}
