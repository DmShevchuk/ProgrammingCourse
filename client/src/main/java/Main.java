import run.Client;
import utils.CommandLine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

public class Main {
    private static String HOST = "127.0.0.1";
    private static int PORT = 12012;

    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                HOST = args[0].split(":")[0];
                PORT = Integer.parseInt(args[0].split(":")[1]);
            } catch (ClassCastException e) {
                HOST = "127.0.0.1";
                PORT = 12012;
                System.out.println(String.format("Wrong connection address, using {%s}:{%d} instead!", HOST, PORT));
            }
        }


        Client client = new Client(new InetSocketAddress(HOST, PORT));

        CommandLine commandLine = new CommandLine();
        try {
            client.connect();
            System.out.println("Соединение установлено!");
        } catch (IOException e) {
            System.out.println("Unable to connect to server!");
        }

        try {
            commandLine.run(client);
        } catch (NoSuchElementException e) {
            System.out.println("Application closed!");
        }
    }
}
