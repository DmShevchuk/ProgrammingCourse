package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InfoWindowController implements Initializable {
    public PieChart typePieChart;
    public BarChart<String, Integer> creationDateBarChart;
    public BarChart<String, Integer> ownerBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCreationDateBarChart(Map<String, Integer> data) {
        ObservableList<XYChart.Data<String, Integer>> dateInfo = FXCollections.observableArrayList();
        for(String month: data.keySet()){
            dateInfo.add(new XYChart.Data<>(month, data.get(month)));
        }
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>(dateInfo);
        ObservableList<XYChart.Series<String, Integer>> series = FXCollections.observableArrayList();
        dataSeries.setName("Количество драконов по месяцам");
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
        dataSeries.setName("Количество драконов у владельцев");
        series.add(dataSeries);
        ownerBarChart.setData(series);
    }


    public void setTypePieChart(Map<String, Integer> data) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(String type: data.keySet()){
            pieData.add(new PieChart.Data(type, data.get(type)));
        }
        typePieChart.setData(pieData);
        typePieChart.setTitle("Тип драконов");

    }
}
