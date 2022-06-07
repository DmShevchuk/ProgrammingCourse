package gui.connectors;

import commands.Command;
import commands.CommandFactory;
import gui.controllers.Controller;
import gui.controllers.MainWindowController;
import interaction.Response;

import java.io.IOException;

public class ScriptRunner implements Connector{
    private MainWindowController controller;
    private final String script;
    private final CommandFactory commandFactory;

    public ScriptRunner(String script, CommandFactory commandFactory){
        this.script = script;
        this.commandFactory = commandFactory;
    }


    public Response start() throws IOException {
        controller.showScriptField.setText(script);
        Command executeScript = commandFactory.getCommand("execute_script");
        return executeScript.execute(script);
    }

    @Override
    public void bindController(Controller controller) {
        this.controller = (MainWindowController) controller;
    }
}
