package gui.connectors;

import account.Client;
import collection.Dragon;
import commands.Command;
import commands.CommandFactory;
import gui.AppWorker;
import gui.I18N;
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
    private I18N i18n;

    public MainWindowConnector(Client client, CommandFactory commandFactory, AppWorker appWorker) {
        this.client = client;
        this.commandFactory = commandFactory;
        this.appWorker = appWorker;
        this.i18n = I18N.getInstance();
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
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void addDragon(Dragon.Builder dragonBuilder) {
        Command command = commandFactory.getCommand("add");
        try {
            showRequestResult(command.execute(dragonBuilder));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void removeById(Integer id) {
        Command command = commandFactory.getCommand("remove_by_id");
        try {
            showRequestResult(command.execute(id));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void addIfMaxDragon(Dragon.Builder dragonBuilder) {
        Command command = commandFactory.getCommand("add_if_max");
        try {
            showRequestResult(command.execute(dragonBuilder));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void clearCollection() {
        Command command = commandFactory.getCommand("clear");
        try {
            showRequestResult(command.execute(client.getAccount().getId()));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void removeFirstInCollection() {
        Command command = commandFactory.getCommand("remove_first");
        try {
            showRequestResult(command.execute(client.getAccount().getId()));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public void removeAllByHead(Long headSize) {
        Command command = commandFactory.getCommand("remove_all_by_head");
        try {
            showRequestResult(command.execute(headSize));
        } catch (IOException e) {
            showRequestResult(new Response(ResponseStatus.FAIL, i18n.getText("problemsWithServer")));
        }
    }

    public LinkedList<Dragon> updateCollection() throws IOException, ClassNotFoundException {
        Command command = commandFactory.getCommand("update_collection");
        return command.execute("").getDragonList();
    }

    private void showRequestResult(Response response) {
        if (response.getStatus() == ResponseStatus.FAIL) {
            controller.showMessageBox(response.getResult(), Alert.AlertType.WARNING);
        } else {
            controller.showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
            controller.setDragons(response.getDragonList());
        }
    }
}
