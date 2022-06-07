package gui.controllers;

import gui.I18N;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TitledPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class InfoWindowController implements Initializable {
    public PieChart typePieChart;
    public BarChart<String, Integer> creationDateBarChart;
    public BarChart<String, Integer> ownerBarChart;
    public TitledPane typePane;
    public TitledPane datePane;
    public TitledPane ownerPane;
    private I18N i18n;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.i18n = I18N.getInstance();
        changeLanguage();
    }

    public void setCreationDateBarChart(Map<String, Integer> data) {
        ObservableList<XYChart.Data<String, Integer>> dateInfo = FXCollections.observableArrayList();
        for(String month: data.keySet()){
            dateInfo.add(new XYChart.Data<>(month, data.get(month)));
        }
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>(dateInfo);
        ObservableList<XYChart.Series<String, Integer>> series = FXCollections.observableArrayList();
        dataSeries.setName(i18n.getText("dragonsByMonth"));
        series.add(dataSeries);
        creationDateBarChart.setData(series);
    }

    public void setOwnerBarChart(Map<String, Integer> data) {
        ObservableList<XYChart.Data<String, Integer>> dateInfo = FXCollections.observableArrayList();
        for(String login: data.keySet()){
            dateInfo.add(new XYChart.Data<>(login, data.get(login)));
        }
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>(dateInfo);
        ObservableList<XYChart.Series<String, Integer>> series = FXCollections.observableArrayList();
        dataSeries.setName(i18n.getText("dragonsByOwner"));
        series.add(dataSeries);
        ownerBarChart.setData(series);
    }


    public void setTypePieChart(Map<String, Integer> data) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(String type: data.keySet()){
            pieData.add(new PieChart.Data(type, data.get(type)));
        }
        pieData.forEach(unit ->
                unit.nameProperty().bind(
                        Bindings.concat(
                                unit.getName(), " ", String.format("%.0f", unit.pieValueProperty().getValue())
                        )
                )
        );
        typePieChart.setData(pieData);
        typePieChart.setTitle(i18n.getText("typeOfDragon"));

    }

    private void changeLanguage(){
        typePane.setText(i18n.getText("type"));
        datePane.setText(i18n.getText("creationDate"));
        ownerPane.setText(i18n.getText("ownerId"));
    }
}
