package gui.controllers;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;
import gui.DragonTableModel;
import gui.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import lombok.Getter;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EditWindowController implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public Slider sliderX;
    @FXML
    public Slider sliderY;
    @FXML
    public Spinner<Integer> ageSpinner;
    @FXML
    public Spinner<Integer> weightSpinner;
    @FXML
    public CheckBox speakingCheckBox;
    @FXML
    public Spinner<Integer> headSizeSpinner;
    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public Button readyButton;
    @FXML
    public Label errorLabel;
    @FXML
    public Label valueX;
    @FXML
    public Label valueY;
    @FXML
    public Button cancelButton;
    public Label nameLabel;
    public Label coordinatesLabel;
    public Label ageLabel;
    public Label weightLabel;
    public Label headSizeLabel;
    public Label typeLabel;

    private I18N i18n;

    @Getter
    private Dragon.Builder dragonBuilder;

    public void setFields(DragonTableModel dragonTableModel) {
        nameTextField.setText(dragonTableModel.getName());
        sliderX.setValue(dragonTableModel.getX());
        sliderY.setValue(dragonTableModel.getY());
        ageSpinner.getValueFactory().setValue(dragonTableModel.getAge());
        weightSpinner.getValueFactory().setValue(Math.toIntExact(dragonTableModel.getWeight()));
        speakingCheckBox.setSelected(dragonTableModel.getSpeaking());
        headSizeSpinner.getValueFactory().setValue(Math.toIntExact(dragonTableModel.getHeadSize()));
        typeComboBox.getSelectionModel().select(dragonTableModel.getType());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.i18n = I18N.getInstance();
        changeLanguage();
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };

        TextFormatter<Integer> ageFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 0, filter);
        ageSpinner.setEditable(true);
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        ageSpinner.getEditor().setTextFormatter(ageFormatter);

        TextFormatter<Integer> weightFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 1, filter);
        weightSpinner.setEditable(true);
        weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000));
        weightSpinner.getEditor().setTextFormatter(weightFormatter);

        TextFormatter<Integer> headSizeFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 1, filter);
        headSizeSpinner.setEditable(true);
        headSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000));
        headSizeSpinner.getEditor().setTextFormatter(headSizeFormatter);

        typeComboBox.getItems().removeAll(typeComboBox.getItems());
        typeComboBox.getItems().addAll("AIR", "FIRE", "UNDERGROUND", "WATER");
        typeComboBox.getSelectionModel().select(0);

        sliderX.valueProperty().addListener((observable, oldValue, newValue) -> valueX.setText("X: " +
                String.format("%.2f", sliderX.getValue())));
        sliderY.valueProperty().addListener((observable, oldValue, newValue) -> valueY.setText("Y: " +
                String.format("%.2f", sliderY.getValue())));

    }

    public void getDragon() {
        if (nameTextField.getText().trim().length() < 5) {
            errorLabel.setText("Name must be longer than 5 characters!");
            return;
        }
        dragonBuilder = new Dragon.Builder();
        dragonBuilder.setName(nameTextField.getText())
                .setCoordinates(new Coordinates(sliderX.getValue(), sliderY.getValue()))
                .setAge(ageSpinner.getValue())
                .setWeight((long) weightSpinner.getValue())
                .setSpeaking(speakingCheckBox.isSelected())
                .setType(DragonType.getTypeByString(typeComboBox.getValue()))
                .setHead(new DragonHead(headSizeSpinner.getValue()));
        closeEditor();
    }

    public void closeEditor() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void changeLanguage(){
        nameLabel.setText(i18n.getText("name"));
        coordinatesLabel.setText(i18n.getText("coords"));
        ageLabel.setText(i18n.getText("age"));
        weightLabel.setText(i18n.getText("weight"));
        speakingCheckBox.setText(i18n.getText("speak"));
        typeLabel.setText(i18n.getText("type"));
        headSizeLabel.setText(i18n.getText("headSize"));
        readyButton.setText(i18n.getText("ready"));
        cancelButton.setText(i18n.getText("cancel"));
    }
}
