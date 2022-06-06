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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

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
    public Canvas canvas;
    public ColorPicker headColorPicker;
    public ColorPicker bodyColorPicker;
    public TextField searchField;
    public ComboBox<String> searchComboBox;
    public CheckBox concreteSearch;
    public Label loginLabel;
    public TabPane tabPane;
    public ColorPicker wingsColorPicker;
    public ColorPicker pawsColorPicker;
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
    private final ObservableList<DragonTableModel> dragonsSearchResult = FXCollections.observableArrayList();
    private final ArrayList<ArrayList<Integer>> coordinatesList = new ArrayList<>();
    private AppWorker appWorker;
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
        // Указание полей для поиска
        searchComboBox.getItems().removeAll(searchComboBox.getItems());
        searchComboBox.getItems().addAll("id", "Имя", "X", "Y", "Дата создания", "Возраст", "Тип", "Вес", "Говорит",
                "Размер головы", "id владельца");
        searchComboBox.getSelectionModel().select(0);

        searchField.textProperty().addListener((observable, oldVal, newVal) ->{
            search(newVal);
        });

        searchComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldVal, newVal) -> search(searchField.getText())
        );
        canvas.setOnMouseClicked(event -> selectDragonOnCanvas(event.getX(), event.getY()));
    }

    private void fillCanvas(){
        coordinatesList.clear();
        GraphicsContext gContext = canvas.getGraphicsContext2D();
        gContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gContext.setLineWidth(2);
        gContext.setStroke(Color.BLACK);
        // Левая граница
        gContext.strokeLine(0, 0, 0, 482);
        // Правая граница
        gContext.strokeLine(482, 0, 482, 482);
        // Верхняя граница
        gContext.strokeLine(0, 0, 482, 0);
        // Нижняя граница
        gContext.strokeLine(0, 482, 482, 482);
        // oX
        gContext.strokeLine(241, 482, 241, 0);
        // oY
        gContext.strokeLine(0, 241, 482, 241);

        gContext.setLineWidth(1);
        int headW = 20;
        int headH = 20;
        int startAngle = 45;
        int headExtent = 320;
        int bodyW = 10;
        int bodyH = 30;
        int bodyX;
        int bodyY;
        double[] rightWingX = new double[3];
        double[] leftWingX = new double[3];
        double[] rightAndLeftWingY = new double[3];

        for(DragonTableModel dragon: dragonsObservable){
            int xRelative = (int) (((dragon.getX() * 460) / 100) + 241);
            int yRelative = (int) (-((dragon.getY() * 460) / 100) + 241);

            coordinatesList.add(new ArrayList<>(List.of(xRelative, yRelative)));

            bodyX = xRelative + 5;
            bodyY = yRelative + 15;
            rightWingX[0] = xRelative + 15;
            rightWingX[1] = xRelative + 25;
            rightWingX[2] = xRelative + 35;

            leftWingX[0] = xRelative + 5;
            leftWingX[1] = xRelative - 5;
            leftWingX[2] = xRelative - 15;

            rightAndLeftWingY[0] = yRelative + 20;
            rightAndLeftWingY[1] = yRelative + 10;
            rightAndLeftWingY[2] = yRelative + 40;

            if(dragon.getOwnerId().equals(client.getAccount().getId())) {
                Color color = bodyColorPicker.getValue();
                gContext.setFill(color);
            }else{
                gContext.setFill(new Color(0.48, 0.67, 0.67, 1));
            }
            gContext.fillRoundRect(bodyX, bodyY, bodyW, bodyH, 1, 1);

            if(dragon.getOwnerId().equals(client.getAccount().getId())){
                Color color = headColorPicker.getValue();
                gContext.setFill(color);
            }else{
                gContext.setFill(new Color(0.48, 0.67, 0.67, 1));
            }
            gContext.fillArc(xRelative, yRelative, headW, headH, startAngle, headExtent, ArcType.ROUND);

            if(dragon.getOwnerId().equals(client.getAccount().getId())){
                Color color = wingsColorPicker.getValue();
                gContext.setFill(color);
            }else{
                gContext.setFill(new Color(0.48, 0.67, 0.67, 1));
            }

            gContext.fillPolygon(rightWingX, rightAndLeftWingY, 3);
            gContext.fillPolygon(leftWingX, rightAndLeftWingY, 3);

            if(dragon.getOwnerId().equals(client.getAccount().getId())){
                Color color = pawsColorPicker.getValue();
                gContext.setStroke(color);
            }else{
                gContext.setStroke(new Color(0.48, 0.67, 0.67, 1));
            }

            gContext.strokeLine(xRelative + 8, yRelative + 45, xRelative - 2, yRelative + 55);

            gContext.strokeLine(xRelative + 12, yRelative + 45, xRelative + 22, yRelative + 55);

        }

    }

    public void selectDragonOnCanvas(double x, double y){
        System.out.println(x + " " + y);
        if(coordinatesList.size() == 0){
            return;
        }
        double hypotenuse = Math.pow(x, 2) + Math.pow(y, 2);
        DragonTableModel closestDragon = dragonsObservable.get(0);
        double closeX = coordinatesList.get(0).get(0);
        double closeY = coordinatesList.get(0).get(1);
        int idx = 0;
        for(ArrayList<Integer> coordinates: coordinatesList){
            double currX = coordinates.get(0);
            double currY = coordinates.get(1);
            if(Math.abs(hypotenuse - Math.pow(closeX, 2) - Math.pow(closeY, 2)) >=
                    Math.abs(hypotenuse - Math.pow(currX, 2) - Math.pow(currY, 2))){
                closestDragon = dragonsObservable.get(idx);
                closeX = currX;
                closeY = currY;
            }
            idx++;
        }
        tabPane.getSelectionModel().select(0);
        dragonTable.getSelectionModel().select(closestDragon);
    }

    public void setDragons(LinkedList<Dragon> dragonLinkedList) {
        dragonTable.getItems().clear();
        List<DragonTableModel> dtm = new LinkedList<>();
        for (Dragon d : dragonLinkedList) {
            dtm.add(new DragonTableModel(d));
        }
        dragonsObservable = FXCollections.observableList(dtm);
        dragonTable.setItems(dragonsObservable);
        setRowFactory();
        fillCanvas();
    }

    public void setDragons(ObservableList<DragonTableModel> dragons){
        dragonTable.setItems(dragons);
        setRowFactory();
    }

    private void setRowFactory(){
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

    public void setClient(Client client) {
        this.client = client;
        // Установка логина пользователя
        loginLabel.setText(this.client.getAccount().getLogin());
    }

    private void openChangeEditor(DragonTableModel rowData) throws IOException {
        if (appWorker.getClient().getAccount().getId() != rowData.getOwnerId()) {
            showMessageBox("Невозможно редактировать дракона,\nпотому что он принадлежит не вам!", Alert.AlertType.WARNING);
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
                    showMessageBox(response.getResult(), Alert.AlertType.WARNING);
                } else {
                    System.out.println(response.getResult());
                    showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
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
                    showMessageBox(response.getResult(), Alert.AlertType.WARNING);
                } else {
                    showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
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

    private void showMessageBox(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
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
            if (creationDateMap.containsKey(month)) {
                creationDateMap.put(month, creationDateMap.get(month) + 1);
            } else {
                creationDateMap.put(month, 1);
            }
            String ownerId = dragon.getOwnerId().toString();
            if (ownerMap.containsKey(ownerId)) {
                ownerMap.put(ownerId, ownerMap.get(ownerId) + 1);
            } else {
                ownerMap.put(ownerId, 1);
            }
            if (dragonTypeMap.containsKey(dragon.getType())) {
                dragonTypeMap.put(dragon.getType(), dragonTypeMap.get(dragon.getType()) + 1);
            } else {
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
            showMessageBox("Выберите элемент для удаления!", Alert.AlertType.WARNING);
            return;
        }
        if (!selectedDragon.getOwnerId().equals(client.getAccount().getId())) {
            showMessageBox("Невозможно удалить элемент,\nпотому что он принадлежит не вам!", Alert.AlertType.WARNING);
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
            if (response.getStatus() == ResponseStatus.FAIL) {
                showMessageBox(response.getResult(), Alert.AlertType.WARNING);
            } else {
                showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
                setDragons(response.getDragonList());
            }
        } catch (ClassCastException | IOException | ClassNotFoundException e) {
            showMessageBox(e.getMessage(), Alert.AlertType.WARNING);
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
                    showMessageBox(response.getResult(), Alert.AlertType.WARNING);
                } else {
                    showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
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
                showMessageBox(response.getResult(), Alert.AlertType.WARNING);
            } else {
                showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
                setDragons(response.getDragonList());
            }
        } catch (IOException | ClassNotFoundException e) {
            showMessageBox(e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    public void removeAllByHead() {
        DragonTableModel selectedDragon = dragonTable.getSelectionModel().getSelectedItem();
        if (selectedDragon == null) {
            showMessageBox("Выберите элемент для удаления!", Alert.AlertType.WARNING);
            return;
        }
        boolean confirm = getConfirm("Удаление", String.format("Вы уверены, что хотите удалить дракона" +
                " с размером головы %d?", selectedDragon.getHeadSize()));
        if (confirm) {
            try {
                client.send(new Request.Builder()
                        .setCommandName("remove_all_by_head")
                        .setArgs(selectedDragon.getHeadSize().toString())
                        .setRequestType(RequestType.RUN_COMMAND)
                        .setAccount(client.getAccount())
                        .build());
                Response response = client.receive();
                if (response.getStatus() == ResponseStatus.FAIL) {
                    showMessageBox(response.getResult(), Alert.AlertType.WARNING);
                } else {
                    showMessageBox(response.getResult(), Alert.AlertType.INFORMATION);
                    setDragons(response.getDragonList());
                }
            } catch (IOException | ClassNotFoundException e) {
                showMessageBox(e.getMessage(), Alert.AlertType.WARNING);
            }
        }
    }

    public void changeDragonsColor() {
        fillCanvas();
    }

    public void search(String query) {
        dragonsSearchResult.clear();
        Pattern pattern;
        query = query.trim();
        if("".equals(query)){
            clearSearch();
        }

        if (concreteSearch.isSelected()) {
            pattern = Pattern.compile("^" + query.trim() + "$");
        } else {
            pattern = Pattern.compile(query.trim());
        }
        int queryIdx = searchComboBox.getSelectionModel().getSelectedIndex();
        System.out.println(dragonsObservable.size());
        for (DragonTableModel dragon : dragonsObservable) {
            List<String> fields = dragon.getDragonModelAsArray();
            if (pattern.matcher(fields.get(queryIdx).toString()).find()) {
                dragonsSearchResult.add(dragon);
            }
        }
        setDragons(dragonsSearchResult);

    }

    public void clearSearch() {
        searchField.clear();
        setDragons(dragonsObservable);
    }

    public void runGame() {
    }
}