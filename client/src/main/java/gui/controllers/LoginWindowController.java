package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorInfoLabel;
    @FXML
    private ComboBox<String> languageComboBox;

    private LoginWindowConnector connector;

    @FXML
    private void onSignInButton() {
        connector.login(loginField.getCharacters().toString().trim(), passwordField.getCharacters().toString().trim());
    }

    @FXML
    private void onSignUpButton() {
        connector.signUp(loginField.getCharacters().toString().trim(), passwordField.getCharacters().toString().trim());
    }

    public void setConnector(LoginWindowConnector connector) {
        this.connector = connector;
    }

    public void setErrorInfoLabel(String message){
        errorInfoLabel.setText(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageComboBox.getItems().removeAll(languageComboBox.getItems());
        languageComboBox.getItems().addAll("Русский", "Nederlands", "Lietuvių", "English");
        languageComboBox.getSelectionModel().select("Русский");

        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //TODO: Смена языка
        });
    }
}