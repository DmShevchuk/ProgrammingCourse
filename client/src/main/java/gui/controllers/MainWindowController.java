package gui.controllers;

import collection.Dragon;
import gui.AppWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }

    public void setDragons(LinkedList<Dragon> dragonLinkedList) {
        List<DragonTableModel> dtm = new LinkedList<>();
        for (Dragon d : dragonLinkedList) {
            dtm.add(new DragonTableModel(d));
        }
        dragonsObservable = FXCollections.observableList(dtm);
        dragonTable.setItems(dragonsObservable);
    }

    public void setAppWorker(AppWorker aW){
        this.appWorker = aW;
    }

    public void logout() throws IOException {
        appWorker.setLoginWindow();
    }
}