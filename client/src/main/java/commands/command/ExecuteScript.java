package commands.command;

import commands.Command;
import commands.CommandManager;
import data.FileManager;
import exceptions.UnableToReadFileException;
import run.Client;
import utils.CommandLine;
import utils.InputSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ExecuteScript extends Command {
    private final ArrayDeque<String> inputStream = new ArrayDeque<String>();
    private final List<String> usedFiles = new ArrayList<String>();
    private final CommandManager commandManager;

    public ExecuteScript(CommandLine commandLine, CommandManager commandManager) {
        super("execute_script",
                "||{file_name}  read and execute a script from the specified file", 1, commandLine);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(Client client) {
        addInstructions();
        commandLine.setInputSource(InputSource.SCRIPT);
    }

    public void addInstructions() {
        FileManager fileManager = new FileManager(commandLine);

        try {
            fileManager.canRead(commandManager.getARG());
            String file = fileManager.read(commandManager.getARG());

            if (file != null && file.length() > 0 && checkRecursion(file)) {
                String[] commands = file.split("\\n");
                for (int i = commands.length - 1; i > -1; i--) {
                    inputStream.addFirst(commands[i].strip());
                }
            }
        } catch (UnableToReadFileException e) {
            commandLine.errorOut(e.getMessage());
        }
    }

    public String nextLine() {
        try {
            return inputStream.removeFirst();
        } catch (NoSuchElementException e) {
            stopScript();
            return "__stopScript__";
        }
    }

    public void stopScript() {
        inputStream.clear();
        usedFiles.clear();
        commandLine.setInputSource(InputSource.COMMAND);
    }

    public boolean checkRecursion(String file) {
        if (usedFiles.size() > 0 && usedFiles.get(usedFiles.size() - 1).equals(file)) {
            return true;
        }

        for (String f : usedFiles) {
            if (f.equals(file)) {
                commandLine.errorOut(String.format("Attempt to create a collision, file {%s}!", commandManager.getARG()));
                return false;
            }
        }
        usedFiles.add(file);
        return true;
    }
}
