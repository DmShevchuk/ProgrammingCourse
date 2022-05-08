package utils;

import interaction.Account;
import run.Client;

import java.io.Console;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

public class AppStarter {
    private final InetSocketAddress inetSocketAddress;

    public AppStarter(InetSocketAddress inetSocketAddress){
        this.inetSocketAddress = inetSocketAddress;
    }

    public void run(){
        Client client = new Client(inetSocketAddress);
        Console console = System.console();
        Account account = null;

        try {
            client.connect();
            System.out.println("Connection established!");
        } catch (IOException e) {
            System.out.println("Unable to connect to server!");
        }

        try {
            Authorization authorization = new Authorization(client, console);
            while (account == null) {
                account = authorization.startAuth();
            }
            client.setAccount(account);

            CommandLine commandLine = new CommandLine(client);
            commandLine.run();
        } catch (NoSuchElementException e) {
            System.out.println("Application closed!");
        }
    }
}
