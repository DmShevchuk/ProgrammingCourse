package gui.connectors;

import account.Client;
import collection.Dragon;
import commands.Command;
import commands.CommandFactory;
import gui.AppWorker;
import gui.controllers.Controller;
import gui.controllers.MainWindowController;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import interaction.ResponseStatus;
import javafx.scene.control.Alert;
import lombok.Getter;

import java.io.IOException;
import java.util.LinkedList;

public class MainWindowConnector implements Connector {
    private MainWindowController controller;
    private final Client client;
    private final AppWorker appWorker;
    @Getter
    private final CommandFactory commandFactory;

    public MainWindowConnector(Client client, CommandFactory commandFactory, AppWorker appWorker) {
        this.client = client;
        this.commandFactory = commandFactory;
        this.appWorker = appWorker;
    }

    @Override
    public void bindController(Controller controller) {
        this.controller = (MainWindowController) controller;
    }


    public void setLoginWindow() throws IOException {
        appWorker.setLoginWindow();
    }

    public void updateDragon(Dragon.Builder dragonBuilder, Integer id) {
        dragonBuilder.setId(id).setOwnerId(client.getAccount().getId());
        Command command = commandFactory.getCommand("update");
        command.setAdditionalArgs(id);
        try {
            showRequestResult(command.execute(dragonBuilder));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void addDragon(Dragon.Builder dragonBuilder) {
        Command command = commandFactory.getCommand("add");
        try {
            showRequestResult(command.execute(dragonBuilder));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void removeById(Integer id) {
        Command command = commandFactory.getCommand("remove_by_id");
        try {
            showRequestResult(command.execute(id));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void addIfMaxDragon(Dragon.Builder dragonBuilder) {
        Command command = commandFactory.getCommand("add_if_max");
        try {
            showRequestResult(command.execute(dragonBuilder));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void clearCollection() {
        Command command = commandFactory.getCommand("clear");
        try {
            showRequestResult(command.execute(client.getAccount().getId()));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void removeFirstInCollection() {
        Command command = commandFactory.getCommand("remove_first");
        try {
            showRequestResult(command.execute(client.getAccount().getId()));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    public void removeAllByHead(Long headSize) {
        Command command = commandFactory.getCommand("remove_all_by_head");
        try {
            showRequestResult(command.execute(headSize));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, "Невозможно подключиться к серверу, попробуйте позже!"));
        }
    }

    private void showRequestResult(Response response) {
        if (response.getStatus() == ResponseStatus.FAIL) {
            controller.showMessageBox(response.getResult(), Alert.AlertType.WARNING);
        } else {
            controller.showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
            controller.setDragons(response.getDragonList());
        }
    }

    public void notifyNewCollection() {
        controller.waitForNewCollection();

    }

    public LinkedList<Dragon> getNewCollection() {
        try {
            client.send(new Request.Builder()
                    .setRequestType(RequestType.GET_COLLECTION)
                    .setAccount(client.getAccount()).build());
            Response response = client.receive();
            return response.getDragonList();
        } catch (IOException | ClassNotFoundException ignore) {
            return null;
        }
    }
}
