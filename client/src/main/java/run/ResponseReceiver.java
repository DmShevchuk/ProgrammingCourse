package run;

import account.Client;
import gui.connectors.MainWindowConnector;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;


public class ResponseReceiver {
    private final Client client;
    private MainWindowConnector connector;

    public ResponseReceiver(Client client) {
        this.client = client;
    }

    public Response getResponse() throws IOException {
        try {
            Response response = client.receive();
            System.out.println(response.getStatus());
            if (response.getStatus() == ResponseStatus.UPDATE_COLLECTION) {
                connector.notifyNewCollection();
                return null;
            }
            return response;

        } catch (IOException | ClassNotFoundException e) {
            client.resetSocketChannel();
        }
        throw new IOException();
    }

    public void setConnector(MainWindowConnector connector) {
        this.connector = connector;
    }
}


