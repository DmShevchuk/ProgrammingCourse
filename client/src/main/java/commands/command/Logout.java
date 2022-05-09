package commands.command;

import commands.Command;
import run.Client;
import utils.AppStarter;
import utils.CommandLine;

import java.net.InetSocketAddress;

public class Logout extends Command {
    private final Client client;

    public Logout(CommandLine commandLine, Client client){
        super("logout", "|| log out of your account", 0, commandLine);
        this.client = client;

    }

    @Override
    public void execute() {
        commandLine.successOut("You have logged out of your account!");
        InetSocketAddress inetSocketAddress = client.getInetSocketAddress();
        client.resetSocketChannel();
        AppStarter appStarter = new AppStarter(inetSocketAddress);
        appStarter.run();
    }
}
