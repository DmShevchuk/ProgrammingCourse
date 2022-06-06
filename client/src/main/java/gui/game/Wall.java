package gui.game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Wall extends Pane {
    Rectangle rectangle;
    private int height;
    public Wall(int height){
        this.height = height;
        rectangle = new Rectangle(20, height);
        getChildren().add(rectangle);
    }
}
