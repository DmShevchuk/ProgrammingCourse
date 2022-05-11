import utils.AppStarter;

import java.net.InetSocketAddress;

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
        AppStarter appStarter = new AppStarter(new InetSocketAddress(HOST, PORT));
        appStarter.run();
    }
}
