package commands.command;

import commands.Command;
import commands.CommandManager;

import java.util.ArrayList;
import java.util.List;


public class ExecuteScript extends Command {
    private final ArrayList<String> instructionList = new ArrayList<>();
    private final List<String> usedFiles = new ArrayList<String>();
    private final CommandManager commandManager;

    public ExecuteScript(CommandManager commandManager) {
        super("execute_script",
                "||{file_name}  read and execute a script from the specified file", 1);
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        addInstructions();
    }

    public void addInstructions() {
//        FileManager fileManager = new FileManager(commandLine);
//
//        if (fileManager.canRead(commandManager.getArg())) {
//
//            String file = fileManager.read(commandManager.getArg());
//
//            if (file != null && file.length() > 0 && !isCreatingRecursion(file)) {
//                String[] commands = file.split("\\n");
//                for (String command : commands) {
//                    instructionList.add(command.trim());
//                }
//                commandLine.setInputSource(InputSource.SCRIPT);
//                setInstructionList();
//            }
//        } else {
//            commandLine.errorOut(String.format("Unable to read file '%s'", commandManager.getArg()));
//        }
    }

    private void setInstructionList() {
//        commandLine.addScriptInstructions(instructionList);
//        instructionList.clear();
    }

    public void clearFields() {
        usedFiles.clear();
    }

    public boolean isCreatingRecursion(String file) {
//        if (usedFiles.size() > 0 && usedFiles.get(usedFiles.size() - 1).equals(file)) {
//            return false;
//        }
//
//        for (String f : usedFiles) {
//            if (f.equals(file)) {
//                commandLine.errorOut(String.format("Attempt to create a collision, file %s!", commandManager.getArg()));
//                return true;
//            }
//        }
//        usedFiles.add(file);
        return false;
    }
}
