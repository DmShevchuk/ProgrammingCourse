package run;

import utils.CommandLine;

public class ServerErrorHandler {
    private final Client client;
    private final CommandLine commandLine;

    public ServerErrorHandler(Client client, CommandLine commandLine){
        this.client = client;
        this.commandLine = commandLine;
    }

    public void handleServerError(){
        commandLine.errorOut("Unable to access server, please try again later!");
        commandLine.showOfflineCommands();
        client.resetSocketChannel();
    }
}
