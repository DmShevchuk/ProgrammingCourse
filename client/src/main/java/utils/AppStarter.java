package utils;

import account.Client;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AppStarter {
    private final InetSocketAddress inetSocketAddress;

    public AppStarter(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public Client run() {
        Client client = new Client(inetSocketAddress);
        try {client.connect();} catch (IOException ignored) {}
        return client;
    }
}
