package gui.controllers;

import account.Client;
import collection.Dragon;
import gui.AppWorker;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import interaction.ResponseStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

public class MainWindowController extends Window implements Initializable {
    @FXML
    public Button addElemButton;
    @FXML
    public TableColumn<DragonTableModel, Integer> ownerId;
    public Circle updateCircle;
    public Label errorLabel;
    public Button getInfo;
    public Button deleteDragon;
    public Button addIfMaxButton;
    public Canvas axis;
    @FXML
    private TableView<DragonTableModel> dragonTable;
    @FXML
    public TableColumn<DragonTableModel, Integer> id;
    @FXML
    public TableColumn<DragonTableModel, String> name;
    @FXML
    public TableColumn<DragonTableModel, Double> x;
    @FXML
    public TableColumn<DragonTableModel, Double> y;
    @FXML
    public TableColumn<DragonTableModel, LocalDate> creationDate;
    @FXML
    public TableColumn<DragonTableModel, Integer> age;
    @FXML
    public TableColumn<DragonTableModel, Long> weight;
    @FXML
    public TableColumn<DragonTableModel, Boolean> speaking;
    @FXML
    public TableColumn<DragonTableModel, Long> headSize;
    @FXML
    public TableColumn<DragonTableModel, String> type;
    @Getter
    private ObservableList<DragonTableModel> dragonsObservable;
    private AppWorker appWorker;
    @Setter
    private Client client;
    @FXML
    public Button updateTableButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Определение таблицы для драконов
        dragonTable.setEditable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("y"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        speaking.setCellValueFactory(new PropertyValueFactory<>("speaking"));
        headSize.setCellValueFactory(new PropertyValueFactory<>("headSize"));
        ownerId.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        // Отключение кнопки "Обновить таблицу"
        updateTableButton.setDisable(true);
    }

    public void setDragons(LinkedList<Dragon> dragonLinkedList) {
        dragonTable.getItems().clear();
        List<DragonTableModel> dtm = new LinkedList<>();
        for (Dragon d : dragonLinkedList) {
            dtm.add(new DragonTableModel(d));
        }
        dragonsObservable = FXCollections.observableList(dtm);
        dragonTable.setItems(dragonsObservable);
        dragonTable.setRowFactory(tableView -> {
            TableRow<DragonTableModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DragonTableModel rowData = row.getItem();
                    try {
                        openChangeEditor(rowData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public void setAppWorker(AppWorker aW) {
        this.appWorker = aW;
    }

    public void logout() throws IOException {
        appWorker.setLoginWindow();
    }

    private void openChangeEditor(DragonTableModel rowData) throws IOException {
        if (appWorker.getClient().getAccount().getId() != rowData.getOwnerId()) {
            showMessageBox("Невозможно редактировать дракона,\nпотому что он принадлежит не вам!");
            return;
        }

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/editor-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/editor-view.fxml"));
        Parent root = fxmlLoader.load();
        EditWindowController editWindowController = fxmlLoader.getController();
        editWindowController.setFields(rowData);
        stage.setScene(new Scene(root));
        stage.setTitle("Editor");
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (editWindowController.getDragonBuilder() != null) {
            Dragon.Builder dragonBuilder = editWindowController.getDragonBuilder();
            dragonBuilder.setId(rowData.getId())
                    .setOwnerId(appWorker.getClient().getAccount().getId())
                    .setCreationDate(rowData.getCreationDate());
            try {
                client.send(new Request.Builder()
                        .setCommandName("update")
                        .setArgs(rowData.getId().toString())
                        .setDragonBuild(dragonBuilder)
                        .setRequestType(RequestType.RUN_COMMAND)
                        .setAccount(client.getAccount())
                        .build());
                Response response = client.receive();
                if (response.getStatus() == ResponseStatus.FAIL) {
                    showMessageBox(response.getResult());
                } else {
                    System.out.println(response.getResult());
                    showMessageBox(response.getResult());
                    setDragons(response.getDragonList());
                }
            } catch (IOException | ClassNotFoundException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    public void openAddEditor() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/editor-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/editor-view.fxml"));
        Parent root = fxmlLoader.load();
        EditWindowController editWindowController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Editor");
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (editWindowController.getDragonBuilder() != null) {
            Dragon.Builder dragonBuilder = editWindowController.getDragonBuilder();
            dragonBuilder.setOwnerId(appWorker.getClient().getAccount().getId());
            try {
                client.send(new Request.Builder()
                        .setCommandName("add")
                        .setDragonBuild(dragonBuilder)
                        .setRequestType(RequestType.RUN_COMMAND)
                        .setAccount(client.getAccount())
                        .build());
                Response response = client.receive();
                if (response.getStatus() == ResponseStatus.FAIL) {
                    showMessageBox(response.getResult());
                } else {
                    showMessageBox(response.getResult());
                    setDragons(response.getDragonList());
                }
            } catch (IOException | ClassNotFoundException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    public void updateTable() {
    }

    private boolean getConfirm(String action, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(action);
        alert.setHeaderText(question);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null || option.get() == ButtonType.CANCEL) {
            return false;
        } else return option.get() == ButtonType.OK;
    }

    private void showMessageBox(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void getInfo() throws IOException {
        Map<String, Integer> creationDateMap = new HashMap<>();
        Map<String, Integer> ownerMap = new HashMap<>();
        Map<String, Integer> dragonTypeMap = new HashMap<>();
        for (DragonTableModel dragon : dragonsObservable) {
            String month = new DateFormatSymbols().getMonths()[dragon.getCreationDate().getMonthValue() - 1];
            if(creationDateMap.containsKey(month)){
                creationDateMap.put(month, creationDateMap.get(month) + 1);
            }else{
                creationDateMap.put(month, 1);
            }
            String ownerId = dragon.getOwnerId().toString();
            if(ownerMap.containsKey(ownerId)){
                ownerMap.put(ownerId, ownerMap.get(ownerId) + 1);
            }else{
                ownerMap.put(ownerId, 1);
            }
            if(dragonTypeMap.containsKey(dragon.getType())){
                dragonTypeMap.put(dragon.getType(), dragonTypeMap.get(dragon.getType()) + 1);
            }else{
                dragonTypeMap.put(dragon.getType(), 1);
            }
        }

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/info-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/info-view.fxml"));
        Parent root = fxmlLoader.load();
        InfoWindowController infoViewController = fxmlLoader.getController();
        infoViewController.setCreationDateBarChart(creationDateMap);
        infoViewController.setOwnerBarChart(ownerMap);
        infoViewController.setTypePieChart(dragonTypeMap);
        stage.setScene(new Scene(root));
        stage.setTitle("Информация");
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void deleteDragon() {
        DragonTableModel selectedDragon = dragonTable.getSelectionModel().getSelectedItem();
        if (selectedDragon == null) {
            showMessageBox("Выберите элемент для удаления!");
            return;
        }
        if (!selectedDragon.getOwnerId().equals(client.getAccount().getId())) {
            showMessageBox("Невозможно удалить элемент,\nпотому что он принадлежит не вам!");
            return;
        }
        boolean confirm = getConfirm("Удаление", String.format("Вы уверены, что хотите удалить дракона %s?",
                selectedDragon.getName()));
        if (!confirm) {
            return;
        }
        try {
            client.send(new Request.Builder()
                    .setCommandName("remove_by_id")
                    .setArgs(selectedDragon.getId().toString())
                    .setRequestType(RequestType.RUN_COMMAND)
                    .setAccount(client.getAccount())
                    .build());
            Response response = client.receive();
            setDragons(response.getDragonList());
            showMessageBox(response.getResult());
        } catch (ClassCastException | IOException | ClassNotFoundException e) {
            showMessageBox(e.getMessage());
        }
    }

    public void openAddIfMaxEditor() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/editor-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/editor-view.fxml"));
        Parent root = fxmlLoader.load();
        EditWindowController editWindowController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Editor");
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (editWindowController.getDragonBuilder() != null) {
            Dragon.Builder dragonBuilder = editWindowController.getDragonBuilder();
            dragonBuilder.setOwnerId(client.getAccount().getId());
            dragonBuilder.setOwnerId(appWorker.getClient().getAccount().getId());
            try {
                client.send(new Request.Builder()
                        .setCommandName("add_if_max")
                        .setDragonBuild(dragonBuilder)
                        .setRequestType(RequestType.RUN_COMMAND)
                        .setAccount(client.getAccount())
                        .build());
                Response response = client.receive();
                if (response.getStatus() == ResponseStatus.FAIL) {
                    showMessageBox(response.getResult());
                } else {
                    showMessageBox(response.getResult());
                    setDragons(response.getDragonList());
                }
            } catch (IOException | ClassNotFoundException e) {
                errorLabel.setText(e.getMessage());
            }
        }

    }

    public void clearCollection() {
        boolean confirm = getConfirm("Очистка коллекции", "Вы уверены, что хотите удалить" +
                " все элементы коллекции?");
        if (!confirm) {
            return;
        }
        try {
            client.send(new Request.Builder()
                    .setCommandName("clear")
                    .setRequestType(RequestType.RUN_COMMAND)
                    .setAccount(client.getAccount())
                    .build());
            Response response = client.receive();
            if (response.getStatus() == ResponseStatus.FAIL) {
                showMessageBox(response.getResult());
            } else {
                showMessageBox(response.getResult());
                setDragons(response.getDragonList());
            }
        } catch (IOException | ClassNotFoundException e) {
            showMessageBox(e.getMessage());
        }
    }
}