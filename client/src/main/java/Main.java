import run.Client;
import utils.CommandLine;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static String HOST = "127.0.0.1";
    private static int PORT = 12012;

    public static void main(String[] args) {
        greetingMessage();

        if (args.length != 0) {
            try {
                HOST = args[0].split(":")[0];
                PORT = Integer.getInteger(args[0].split(":")[1]);
            } catch (ClassCastException e) {
                HOST = "127.0.0.1";
                PORT = 12012;
                System.out.println(String.format("Wrong connection address, using {%s}:{%d} instead!", HOST, PORT));
            }
        }

        CommandLine commandLine = new CommandLine();
        Client client = new Client(new InetSocketAddress(HOST, PORT));
        try {
            client.connect();
        } catch (IOException e){
            System.out.println("Невозможно подключиться к серверу!");
        }

        commandLine.run(client);
    }

    public static void greetingMessage(){
        System.out.println("""
                 _____                                 \s
                |  __ \\                                \s
                | |  | |_ __ __ _  __ _  ___  _ __  ___\s
                | |  | | '__/ _` |/ _` |/ _ \\| '_ \\/ __|
                | |__| | | | (_| | (_| | (_) | | | \\__ \\
                |_____/|_|  \\__,_|\\__, |\\___/|_| |_|___/
                                   __/ |               \s
                                  |___/                \s
                                """);
    }
}
