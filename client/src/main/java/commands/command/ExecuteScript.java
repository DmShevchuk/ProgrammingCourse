package commands.command;

import commands.Command;
import commands.CommandFactory;
import exceptions.IncorrectCommandException;
import interaction.Response;
import interaction.ResponseStatus;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ExecuteScript extends Command {
    private final ArrayList<String> instructionList = new ArrayList<>();
    private String results = "";
    private final RequestSender sender;
    private final ResponseReceiver receiver;
    private final CommandFactory commandFactory;

    public ExecuteScript(RequestSender sender, ResponseReceiver receiver, CommandFactory commandFactory) {
        super("execute_script",
                "||{file_name}  read and execute a script from the specified file", 1);
        this.sender = sender;
        this.receiver = receiver;
        this.commandFactory = commandFactory;
    }

    public void addInstructions(String text) {
        results = "";
        String[] lines = text.split("\\n");
        Collections.addAll(instructionList, lines);
    }


    @Override
    public <T> Response execute(T args) throws IOException {
        addInstructions((String) args);
        for (String line : instructionList) {
            try {
                commandFactory.recognizeCommand(line);
                Command command = commandFactory.getCommand(line.trim().split("\\s")[0]);
                Response response;
                if (command.getArgQuantity() == 1) {
                    response = command.execute(line.trim().split("\\s")[1]);
                } else {
                    response = command.execute(line.trim().split("\\s")[0]);
                }
                results += "-----\n" + response.getResult() + "\n";
            } catch (IncorrectCommandException e) {
                results += "-----\n" + e.getMessage() + "\n";
            }
        }
        instructionList.clear();
        return new Response(ResponseStatus.INFO, results);
    }
}
