package gui.controllers;

import account.Client;
import collection.Dragon;
import data.FileManager;
import gui.DragonTableModel;
import gui.I18N;
import gui.connectors.Connector;
import gui.connectors.MainWindowConnector;
import gui.connectors.ScriptRunner;
import gui.game.FlappyDragon;
import interaction.Response;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class MainWindowController extends Window implements Initializable, Controller {
    @FXML
    public Button addElemButton;
    @FXML
    public TableColumn<DragonTableModel, Integer> ownerId;
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
    public TextField headSizeField;
    public TextArea showScriptField;
    public TextArea showScriptResultField;
    public Label currentUser;
    public ComboBox<String> languageComboBox;
    public Button logoutButton;
    public Button removeFirst;
    public Button removeByHead;
    public Button clearCollectionButton;
    public Label yourColorsLabel;
    public Label headColorLabel;
    public Label bodyColorLabel;
    public Label wingsColorsLabel;
    public Label pawsColorsLabel;
    public Button runGameButton;
    public Button runScriptButton;
    public Button uploadFileButton;
    public Button clearFieldButton;
    public ProgressIndicator updateTableProgressIndicator;
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
    private List<ArrayList<Integer>> coordinatesList = new ArrayList<>();
    private Client client;
    private MainWindowConnector connector;
    @FXML
    public Button updateTableButton;
    private I18N i18n;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18n = I18N.getInstance();
        languageComboBox.getItems().removeAll(languageComboBox.getItems());
        languageComboBox.getItems().addAll("Русский", "Nederlands", "Lietuvių", "English");
        languageComboBox.getSelectionModel().select(i18n.getLanguage());
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            changeLanguage(newValue);
        });
        changeLanguage(i18n.getLanguage());
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


        searchField.textProperty().addListener((observable, oldVal, newVal) -> {
            search(newVal);
        });

        // При вводе в поле для поиска сразу идет поиск по указанному полю
        searchComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldVal, newVal) -> search(searchField.getText())
        );

        // При клике мышкой по координатному полю показывается объект в таблице
        canvas.setOnMouseClicked(event -> selectDragonOnCanvas(event.getX(), event.getY()));
    }

    /**
     * Метод для отрисовки драконов исключительно с помощью ГРАФИЧЕСКИХ ПРИМИТИВОВ
     * При этом есть возможность изменять цвет головы, туловища, лап и крыльев дракона
     */
    private void fillCanvas() {
        coordinatesList.clear();
        GraphicsContext gContext = canvas.getGraphicsContext2D();
        gContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CanvasDrawer canvasDrawer = new CanvasDrawer(dragonsObservable, gContext);
        canvasDrawer.drawBorders();
        canvasDrawer.drawAxis();

        coordinatesList = canvasDrawer.drawDragons(client.getAccount().getId(),
                bodyColorPicker.getValue(),
                headColorPicker.getValue(),
                wingsColorPicker.getValue(),
                pawsColorPicker.getValue());
    }

    /**
     * Метод для поиска ближайшего дракона по клику мыши
     * Как только такой дракон найден, происходит переход к таблице
     * Расстояние от точки клика до координат дракона вычисляется по стандартной формуле расстояния между точками
     * |AB| = sqrt((x1 - x0)^2 + (y1 - y0)^2)
     * При этом, если клик произошел слишком далеко от драконов, перехода не происходит
     */
    public void selectDragonOnCanvas(double x, double y) {
        if (coordinatesList.size() == 0) {
            return;
        }
        DragonTableModel closestDragon = dragonsObservable.get(0);
        double closeX = coordinatesList.get(0).get(0);
        double closeY = coordinatesList.get(0).get(1);
        int idx = 0;
        for (ArrayList<Integer> coordinates : coordinatesList) {
            double currX = coordinates.get(0);
            double currY = coordinates.get(1);
            if (Math.sqrt(Math.pow(x - closeX, 2) + Math.pow(y - closeY, 2)) >=
                    Math.sqrt(Math.pow(x - currX, 2) + Math.pow(y - currY, 2))) {
                closestDragon = dragonsObservable.get(idx);
                closeX = currX;
                closeY = currY;
            }
            idx++;
        }
        if (Math.sqrt(Math.pow(x - closeX, 2) + Math.pow(y - closeY, 2)) >= 30) {
            return;
        }
        setDragons(dragonsObservable);
        tabPane.getSelectionModel().select(0);
        dragonTable.getSelectionModel().select(closestDragon);
    }

    /**
     * Метод для установки полей драконов в таблицу
     *
     * @param dragonLinkedList: LinkedList<Dragon>
     */
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

    /**
     * Метод для установки полей драконов в таблицу
     *
     * @param dragons: ObservableList<DragonTableModel>
     */
    public void setDragons(ObservableList<DragonTableModel> dragons) {
        dragonTable.setItems(dragons);
        setRowFactory();
    }


    /**
     * При двойном клике мышью по строке таблицы происходит переход на редактирование
     */
    private void setRowFactory() {
        dragonTable.setRowFactory(tableView -> {
            TableRow<DragonTableModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DragonTableModel rowData = row.getItem();
                    try {
                        openUpdateEditor(rowData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public void logout() throws IOException {
        connector.setLoginWindow();
    }

    public void setClient(Client client) {
        this.client = client;
        // Установка логина пользователя
        loginLabel.setText(this.client.getAccount().getLogin() + "- id@" + this.client.getAccount().getId());
    }


    /**
     * Открыть окно для редактирования драконов
     * Если rowData == null, открывается окно с пустыми полями для добавления дракона
     * Иначе открывается окно для редактирования с заполненными полями
     */
    private Dragon.Builder openEditor(DragonTableModel rowData) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/editor-view.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/editor-view.fxml"));
        Parent root = fxmlLoader.load();
        EditWindowController editWindowController = fxmlLoader.getController();

        if (rowData != null) {
            editWindowController.setFields(rowData);
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Editor");
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return editWindowController.getDragonBuilder();
    }

    /**
     * Открытие окна для редактирования дракона из таблицы
     */
    private void openUpdateEditor(DragonTableModel rowData) throws IOException {
        if (client.getAccount().getId() != rowData.getOwnerId()) {
            showMessageBox(i18n.getText("unableToEditDragon"),
                    Alert.AlertType.WARNING);
            return;
        }

        Dragon.Builder dragonBuilder = openEditor(rowData);

        if (dragonBuilder != null) {
            dragonBuilder.setCreationDate(rowData.getCreationDate());
            connector.updateDragon(dragonBuilder, rowData.getId());
        }
    }

    public void openAddEditor() throws IOException {
        Dragon.Builder dragonBuilder = openEditor(null);

        if (dragonBuilder != null) {
            dragonBuilder.setOwnerId(client.getAccount().getId());
            connector.addDragon(dragonBuilder);
        }
    }

    public void updateTable() {
        LinkedList<Dragon> dragons = new LinkedList<>();
        try {
            dragons = connector.updateCollection();
        }catch (IOException | ClassNotFoundException e){
            showMessageBox(i18n.getText("unableToUpdateCollection"), Alert.AlertType.WARNING);
        }
        setDragons(dragons);
    }

    private boolean getConfirm(String action, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(action);
        alert.setHeaderText(question);

        Optional<ButtonType> option = alert.showAndWait();

        if (option == null || option.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return option.get() == ButtonType.OK;
        }
    }

    public void showMessageBox(String message, Alert.AlertType alertType) {
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
        stage.setTitle(i18n.getText("info"));
        stage.initOwner(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void deleteDragon() {
        DragonTableModel selectedDragon = dragonTable.getSelectionModel().getSelectedItem();
        if (selectedDragon == null) {
            showMessageBox(i18n.getText("chooseElementToDelete"), Alert.AlertType.WARNING);
            return;
        }
        if (!selectedDragon.getOwnerId().equals(client.getAccount().getId())) {
            showMessageBox(i18n.getText("unableToDeleteElem"), Alert.AlertType.WARNING);
            return;
        }
        boolean confirm = getConfirm(i18n.getText("delete"), i18n.getText("deleteConfirm") +
                selectedDragon.getName() + "?");
        if (!confirm) {
            return;
        }
        connector.removeById(selectedDragon.getId());
    }

    public void openAddIfMaxEditor() throws IOException {
        Dragon.Builder dragonBuilder = openEditor(null);

        if (dragonBuilder != null) {
            dragonBuilder.setOwnerId(client.getAccount().getId());
            connector.addIfMaxDragon(dragonBuilder);
        }

    }

    public void clearCollection() {
        boolean confirm = getConfirm(i18n.getText("clear"), i18n.getText("deleteAllElemConfirm"));
        if (!confirm) {
            return;
        }
        connector.clearCollection();
    }

    public void removeFirstInCollection() {
        boolean confirm = getConfirm(i18n.getText("clear"), i18n.getText("deleteFirstElemConfirm"));
        if (!confirm) {
            return;
        }
        connector.removeFirstInCollection();
    }

    public void removeAllByHead() {
        Long headSizeValue = 0L;
        try {
            headSizeValue = Long.parseLong(headSizeField.getText().trim());
        } catch (NumberFormatException e) {
            showMessageBox(i18n.getText("enterCorrectHeadSize"), Alert.AlertType.WARNING);
            return;
        }
        boolean confirm = getConfirm(i18n.getText("delete"),
                i18n.getText("deleteByHeadConfirm") + " " + headSizeValue + "?");
        if (confirm) {
            connector.removeAllByHead(headSizeValue);
        }
    }

    public void changeDragonsColor() {
        fillCanvas();
    }

    public void search(String query) {
        dragonsSearchResult.clear();
        Pattern pattern;
        query = query.trim();
        if ("".equals(query)) {
            clearSearch();
        }

        if (concreteSearch.isSelected()) {
            pattern = Pattern.compile("^" + query.trim() + "$");
        } else {
            pattern = Pattern.compile(query.trim());
        }
        int queryIdx = searchComboBox.getSelectionModel().getSelectedIndex();
        if (queryIdx < 0) {
            return;
        }
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
        try {
            Stage stage = new Stage();
            FlappyDragon game = new FlappyDragon();
            game.start(stage);
            stage.showAndWait();
        } catch (RuntimeException ignored) {
        }
    }

    @Override
    public void bindConnector(Connector connector) {
        this.connector = (MainWindowConnector) connector;
    }

    /**
     * Метод дял открытия файла со скриптом
     */
    public void openScriptFile() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Text", "*.txt"));
        fileChooser.setTitle("Open Script FIle");
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }
        FileManager fileManager = new FileManager();
        if (!fileManager.canRead(file.getAbsolutePath())) {
            showMessageBox(i18n.getText("cannotReadFile"), Alert.AlertType.WARNING);
        }
        String script = fileManager.read(file.getAbsolutePath());
        showScriptField.setText(script);
    }

    public void runScript() {
        String[] lines = showScriptField.getText().split("\\n");
        showScriptField.clear();
        showScriptResultField.clear();
        String newLines = "";
        for (String line : lines) {
            // Запрет на выполнение команд
            if (!line.startsWith("update") && !line.startsWith("add") && !line.startsWith("history")
                    && !line.startsWith("execute_script") && !"".equals(line.trim())) {
                newLines += line + "\n";
            }
        }
        showScriptField.setText(newLines);

        ScriptRunner scriptRunner = new ScriptRunner(newLines, connector.getCommandFactory());
        scriptRunner.bindController(this);

        try {
            Response response = scriptRunner.start();
            showScriptResultField.setText(response.getResult());
        } catch (IOException e) {
            showMessageBox(e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    public void clearScriptField() {
        showScriptField.clear();
        showScriptResultField.clear();
    }

    private void changeLanguage(String newValue) {
        i18n.changeLocale(newValue);
        currentUser.setText(i18n.getText("currentUser"));
        logoutButton.setText(i18n.getText("logout"));

        searchComboBox.getItems().removeAll(searchComboBox.getItems());
        searchComboBox.getItems().addAll("id",
                i18n.getText("name"),
                "X", "Y",
                i18n.getText("creationDate"),
                i18n.getText("age"),
                i18n.getText("type"),
                i18n.getText("weight"),
                i18n.getText("speak"),
                i18n.getText("headSize"),
                i18n.getText("ownerId"));
        searchComboBox.getSelectionModel().select(0);

        updateTableButton.setText(i18n.getText("updateTable"));
        ObservableList<Tab> tabs = tabPane.getTabs();
        tabs.get(0).setText(i18n.getText("table"));
        tabs.get(1).setText(i18n.getText("visual"));
        tabs.get(2).setText(i18n.getText("script"));
        ObservableList<TableColumn<DragonTableModel, ?>> columns = dragonTable.getColumns();
        columns.get(1).setText(i18n.getText("name"));
        columns.get(2).setText(i18n.getText("coords"));
        columns.get(3).setText(i18n.getText("creationDate"));
        columns.get(4).setText(i18n.getText("age"));
        columns.get(5).setText(i18n.getText("type"));
        columns.get(6).setText(i18n.getText("weight"));
        columns.get(7).setText(i18n.getText("speak"));
        columns.get(8).setText(i18n.getText("headSize"));
        columns.get(9).setText(i18n.getText("ownerId"));

        getInfo.setText(i18n.getText("info"));
        removeFirst.setText(i18n.getText("removeFirst"));
        addIfMaxButton.setText(i18n.getText("addIfMax"));
        clearCollectionButton.setText(i18n.getText("clear"));
        deleteDragon.setText(i18n.getText("delete"));
        addElemButton.setText(i18n.getText("add"));
        removeByHead.setText(i18n.getText("removeByHead"));

        concreteSearch.setText(i18n.getText("strongSearch"));

        yourColorsLabel.setText(i18n.getText("yoursColor"));
        headColorLabel.setText(i18n.getText("headColor"));
        bodyColorLabel.setText(i18n.getText("bodyColor"));
        wingsColorsLabel.setText(i18n.getText("wingsColor"));
        pawsColorsLabel.setText(i18n.getText("pawsColor"));
        runGameButton.setText(i18n.getText("runGame"));

        uploadFileButton.setText(i18n.getText("uploadFile"));
        clearFieldButton.setText(i18n.getText("clearFields"));
        runScriptButton.setText(i18n.getText("runScript"));

    }

}