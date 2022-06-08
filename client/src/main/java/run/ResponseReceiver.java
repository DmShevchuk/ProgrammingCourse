package run;

import account.Client;
import gui.connectors.MainWindowConnector;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;


public class ResponseReceiver {
    private final Client client;

    public ResponseReceiver(Client client) {
        this.client = client;
    }

    public Response getResponse() throws IOException {
        try {
            return client.receive();
        } catch (IOException | ClassNotFoundException e) {
            client.resetSocketChannel();
        }
        throw new IOException();
    }
}


