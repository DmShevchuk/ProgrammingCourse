package gui.connectors;

import account.Authorization;
import account.Client;
import gui.AppWorker;
import gui.controllers.Controller;
import gui.controllers.LoginWindowController;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;

public class LoginWindowConnector implements Connector {
    private final Client client;
    private LoginWindowController loginWindowController;
    private final AppWorker appWorker;

    public LoginWindowConnector(Client client, AppWorker appWorker) {
        this.client = client;
        this.appWorker = appWorker;
    }

    public void login(String login, String password) {
        if (validateData(login, password)) {
            handleResponse(new Authorization(client).signIn(login, password));
        }

    }

    public void signUp(String login, String password) {
        if (validateData(login, password)) {
            handleResponse(new Authorization(client).signUp(login, password));
        }
    }

    private boolean validateData(String login, String password) {
        if (login.length() >= 3 && password.length() >= 3) {
            return true;
        }
        loginWindowController.setErrorInfoLabel("Очень короткий логин/пароль!");
        return false;
    }

    private void handleResponse(Response response) {
        if (response.getStatus() == ResponseStatus.FAIL) {
            loginWindowController.setErrorInfoLabel(response.getResult());
            return;
        }
        try {
            client.setAccount(response.getAccount());
            appWorker.setMainWindow(response.getDragonList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void bindController(Controller loginWindowController) {
        this.loginWindowController = (LoginWindowController) loginWindowController;
    }
}