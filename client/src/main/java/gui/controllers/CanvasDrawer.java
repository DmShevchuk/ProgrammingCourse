package gui.controllers;

import gui.DragonTableModel;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class CanvasDrawer {
    private final int canvasHeight = 482;
    private final int canvasWidth = 482;

    private final ObservableList<DragonTableModel> dragonsObservable;
    private final GraphicsContext gContext;

    public CanvasDrawer(ObservableList<DragonTableModel> dragonsObservable, GraphicsContext graphicsContext) {
        this.dragonsObservable = dragonsObservable;
        this.gContext = graphicsContext;
    }

    public void drawBorders() {
        gContext.setStroke(javafx.scene.paint.Color.BLACK);
        // Левая граница поля
        gContext.strokeLine(0, 0, 0, canvasHeight);
        // Правая граница поля
        gContext.strokeLine(canvasWidth - 1, 0, canvasWidth - 1, canvasHeight);
        // Верхняя граница поля
        gContext.strokeLine(0, 0, canvasWidth, 0);
        // Нижняя граница поля
        gContext.strokeLine(0, canvasHeight, canvasWidth, canvasHeight);
    }

    public void drawAxis() {
        gContext.setStroke(javafx.scene.paint.Color.BLACK);
        // oX
        gContext.strokeLine(canvasWidth / 2, canvasHeight, canvasWidth / 2, 0);
        // oY
        gContext.strokeLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2);
    }

    public List<ArrayList<Integer>> drawDragons(int ownerId, Color bodyColor, Color headColor, Color wingsColor,
                                                Color pawsColor) {
        List<ArrayList<Integer>> coordinatesList = new ArrayList<>();

        gContext.setLineWidth(1);
        int headW = 15;
        int headH = 15;

        int bodyXDelta = 5;
        int bodyYDelta = 15;

        int startAngle = 45;
        int headExtent = 320;

        int bodyW = 5;
        int bodyH = 15;

        int bodyX;
        int bodyY;

        double[] rightWingX = new double[3];
        double[] leftWingX = new double[3];
        double[] rightAndLeftWingY = new double[3];

        for (DragonTableModel dragon : dragonsObservable) {
            // 100 - проценты
            int xRelative = (int) (((dragon.getX() * (canvasWidth - 20) / 2) / 100) + (canvasWidth / 2));
            int yRelative = (int) (-((dragon.getY() * (canvasHeight - 20) / 2) / 100) + (canvasHeight / 2));

            coordinatesList.add(new ArrayList<>(List.of(xRelative, yRelative)));

            bodyX = xRelative + bodyXDelta;
            bodyY = yRelative + bodyYDelta;
            rightWingX[0] = xRelative + 10;
            rightWingX[1] = xRelative + 20;
            rightWingX[2] = xRelative + 30;

            leftWingX[0] = xRelative + (bodyH / 3);
            leftWingX[1] = xRelative - 5;
            leftWingX[2] = xRelative - 15;

            rightAndLeftWingY[0] = yRelative + 20;
            rightAndLeftWingY[1] = yRelative + 10;
            rightAndLeftWingY[2] = yRelative + 40;

            // Отрисовка туловища
            if (dragon.getOwnerId().equals(ownerId)) {
                gContext.setFill(bodyColor);
            } else {
                gContext.setFill(new javafx.scene.paint.Color(0.48, 0.67, 0.67, 1));
            }
            gContext.fillRoundRect(bodyX, bodyY, bodyW, bodyH, 1, 1);

            // Отрисовка головы
            if (dragon.getOwnerId().equals(ownerId)) {
                gContext.setFill(headColor);
            } else {
                gContext.setFill(new javafx.scene.paint.Color(0.48, 0.67, 0.67, 1));
            }
            gContext.fillArc(xRelative, yRelative, headW, headH, startAngle, headExtent, ArcType.ROUND);

            // Отрисовка крыльев
            if (dragon.getOwnerId().equals(ownerId)) {
                gContext.setFill(wingsColor);
            } else {
                gContext.setFill(new javafx.scene.paint.Color(0.48, 0.67, 0.67, 1));
            }
            gContext.fillPolygon(rightWingX, rightAndLeftWingY, 3);
            gContext.fillPolygon(leftWingX, rightAndLeftWingY, 3);

            // Отрисовка лап
            if (dragon.getOwnerId().equals(ownerId)) {
                gContext.setStroke(pawsColor);
            } else {
                gContext.setStroke(new javafx.scene.paint.Color(0.48, 0.67, 0.67, 1));
            }
            gContext.strokeLine(xRelative + 6, yRelative + 30, xRelative - 2, yRelative + 40);
            gContext.strokeLine(xRelative + 10, yRelative + 30, xRelative + 15, yRelative + 40);
        }

        return coordinatesList;
    }
}