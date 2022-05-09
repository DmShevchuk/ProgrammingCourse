package commands.command;

import commands.Command;
import run.Client;
import utils.AppStarter;
import utils.CommandLine;

import java.net.InetSocketAddress;

/*
    Команда выхода из аккаунта
**/
public class Logout extends Command {
    private final Client client;

    public Logout(CommandLine commandLine, Client client) {
        super("logout", "|| log out of your account", 0, commandLine);
        this.client = client;

    }

    /*
        Сброс соединения и запуск нового аккаунта
    **/
    @Override
    public void execute() {
        commandLine.successOut("You have logged out of your account!");
        InetSocketAddress inetSocketAddress = client.getInetSocketAddress();
        client.refuseConnection();
        AppStarter appStarter = new AppStarter(inetSocketAddress);
        appStarter.run();
    }
}
