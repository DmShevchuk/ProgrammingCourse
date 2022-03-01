package commands;

import data.FileManager;
import utils.CommandLine;
import utils.InputSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ExecuteScript extends Command {
    private static ArrayDeque<String> inputStream = new ArrayDeque<String>();
    private static List<String> usedFiles = new ArrayList<String>();

    public ExecuteScript() {
        super("execute_script file_name : считать и исполнить скрипт из указанного файла",
                "execute_script", 1);
    }

    @Override
    public void execute() {
        addInstructions();
        CommandLine.setInputSource(InputSource.SCRIPT);
    }

    public static void addInstructions() {
        if (FileManager.canRead(CommandManager.getARG())) {

            String file = FileManager.read(CommandManager.getARG());

            if (file != null && file.length() > 0 && checkRecursion(file)) {
                String[] commands = file.split("\\n");

                for (int i = commands.length - 1; i > -1; i--) {
                    inputStream.addFirst(commands[i]);
                }
            }
        }
    }

    public static String nextLine() {
        try {
            return inputStream.removeFirst();
        } catch (NoSuchElementException e) {
            stopScript();
            return "_stop_script_";
        }
    }

    public static void stopScript() {
        inputStream.clear();
        usedFiles.clear();
        CommandLine.setInputSource(InputSource.COMMAND);
    }

    public static boolean checkRecursion(String file) {
        if (usedFiles.size() > 0 && usedFiles.get(usedFiles.size() - 1).equals(file)) {
            return true;
        }

        for (String f : usedFiles) {
            if (f.equals(file)) {
                CommandLine.errorOut(String.format("Попытка создать коллизию, файл %s!", CommandManager.getARG()));
                return false;
            }
        }
        usedFiles.add(file);
        return true;
    }
}
