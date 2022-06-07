package gui;

import account.Client;
import collection.Dragon;
import commands.CommandFactory;
import gui.connectors.Connector;
import gui.connectors.LoginWindowConnector;
import gui.connectors.MainWindowConnector;
import gui.controllers.LoginWindowController;
import gui.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import run.RequestSender;
import run.ResponseReceiver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Класс, отвечающий за взаимодействие двух основных окон - входа/регистрации и главного экрана
 * */
public class AppWorker {
    private final Stage stage;
    @Getter
    private final Client client;

    public AppWorker(Stage stage, Client client) {
        this.stage = stage;
        this.client = client;
    }

    /**
     * Запуск окна входа/регистрации
     * */
    public void setLoginWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/login-view.fxml"));
        I18N.getInstance().setResourceBundle(ResourceBundle.getBundle("i18n.text", Locale.getDefault()));
        fxmlLoader.setLocation(getClass().getResource("/login-view.fxml"));

        Parent root = fxmlLoader.load();
        LoginWindowController loginController = fxmlLoader.getController();
        Connector connector = new LoginWindowConnector(client, this);

        connector.bindController(loginController);
        loginController.bindConnector(connector);

        Scene scene = new Scene(root, 500, 330);
        stage.setTitle("Dragons");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Запуск основного окна
     * */
    public void setMainWindow(LinkedList<Dragon> dragonLinkedList) throws IOException{
        RequestSender requestSender = new RequestSender(client);
        ResponseReceiver responseReceiver = new ResponseReceiver(client);

        CommandFactory commandFactory = new CommandFactory(requestSender, responseReceiver);
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/main-window-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/main-window-view.fxml"));

        Parent root = fxmlLoader.load();
        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.setClient(client);

        mainWindowController.setDragons(dragonLinkedList);
        mainWindowController.setClient(client);

        MainWindowConnector connector = new MainWindowConnector(client, commandFactory,this);
        mainWindowController.bindConnector(connector);
        connector.bindController(mainWindowController);
        responseReceiver.setConnector(connector);
        Scene scene = new Scene(root);
        stage.setTitle("Dragons");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
