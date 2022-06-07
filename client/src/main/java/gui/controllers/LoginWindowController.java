package gui.controllers;

import gui.I18N;
import gui.connectors.Connector;
import gui.connectors.LoginWindowConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable, Controller {
    public Button signInButton;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorInfoLabel;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    public Button signUpButton;

    private LoginWindowConnector connector;
    private I18N i18n;

    @FXML
    private void onSignInButton() {
        connector.login(loginField.getCharacters().toString().trim(), passwordField.getCharacters().toString().trim());
    }

    @FXML
    private void onSignUpButton() {
        connector.signUp(loginField.getCharacters().toString().trim(), passwordField.getCharacters().toString().trim());
    }

    public void setErrorInfoLabel(String message) {
        errorInfoLabel.setText(message);
    }

    @Override
    public void bindConnector(Connector connector) {
        this.connector = (LoginWindowConnector) connector;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.i18n = I18N.getInstance();
        this.i18n.changeLocale("Русский");
        languageComboBox.getItems().removeAll(languageComboBox.getItems());
        languageComboBox.getItems().addAll("Русский", "Nederlands", "Lietuvių", "English");
        languageComboBox.getSelectionModel().select(i18n.getLanguage());
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            changeLanguage(newValue);
        });
        changeLanguage(i18n.getLanguage());
    }

    private void changeLanguage(String language) {
        i18n.changeLocale(language);
        signInButton.setText(i18n.getText("login"));
        signUpButton.setText(i18n.getText("signUp"));
    }
}