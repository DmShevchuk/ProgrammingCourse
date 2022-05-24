package gui.controllers;

import account.Authorization;
import account.Client;
import gui.AppWorker;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.IOException;

public class LoginWindowConnector {
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

    public void setLoginWindowController(LoginWindowController loginWindowController) {
        this.loginWindowController = loginWindowController;
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
            appWorker.setMainWindow();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
