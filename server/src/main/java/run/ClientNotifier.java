package run;

import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientNotifier {
    private ArrayList<Socket> socketList = new ArrayList<>();

    public void addSocket(Socket socket) {
        socketList.add(socket);
    }

    public void notifyClients() {
        ResponseSender sender = new ResponseSender();
        for (Socket socket : socketList) {
            try {
                sender.send(new Response(ResponseStatus.UPDATE_COLLECTION, "Update collection"), socket);
            } catch (IOException e) {
                socketList.remove(socket);
            }
        }
    }
}
