package run;

import account.Client;
import interaction.Request;

import java.io.IOException;

public class RequestSender {
    private final Client client;

    public RequestSender(Client client){
        this.client = client;
    }

    public void send(Request.Builder request) throws IOException {
        client.send(request.setAccount(client.getAccount()).build());
    }
}
