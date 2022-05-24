package gui;

import account.Client;
import gui.controllers.LoginWindowConnector;
import gui.controllers.LoginWindowController;
import gui.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppWorker {
    private LoginWindowController loginController;
    private MainWindowController mainWindowController;
    private final Stage stage;
    private final Client client;

    public AppWorker(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
    }

    public void setLoginWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/login-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/login-view.fxml"));
        Parent root = fxmlLoader.load();
        loginController = fxmlLoader.getController();
        LoginWindowConnector connector = new LoginWindowConnector(client, this);
        connector.setLoginWindowController(loginController);
        loginController.setConnector(connector);
        Scene scene = new Scene(root, 500, 330);
        stage.setTitle("Dragons");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void setMainWindow() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/main-window-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/main-window-view.fxml"));
        Parent root = fxmlLoader.load();
        mainWindowController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Dragons");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
