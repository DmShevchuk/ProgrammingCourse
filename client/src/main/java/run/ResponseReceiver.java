package run;

import interaction.Response;
import interaction.ResponseStatus;
import utils.CommandLine;

import java.io.IOException;

public class ResponseReceiver {
    public ResponseReceiver() {
    }

    public Response getResponse(Client client, CommandLine commandLine) {
        try {
            Response response = client.receive();
            if (response.getStatus() == ResponseStatus.SUCCESS) {
                commandLine.successOut(response.getResult());
            } else if (response.getStatus() == ResponseStatus.FAIL) {
                commandLine.errorOut(response.getResult());
            } else {
                return response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


