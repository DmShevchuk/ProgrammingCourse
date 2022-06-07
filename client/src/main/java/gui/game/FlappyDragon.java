package gui.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyDragon extends Application {
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public static List<Wall> walls = new ArrayList<>();
    DragonSprite dragonSprite = new DragonSprite();
    public static int score = 0;
    public Label scoreLabel = new Label("Score :" + score);

    public Parent createContent() {
        gameRoot.setPrefSize(600, 600);
        for (int i = 0; i < 100; i++) {
            int enter = (int) (Math.random() * 100 + 50);
            int height = new Random().nextInt(600 - enter);
            Wall wall = new Wall(height);
            wall.setTranslateX(i * 350 + 600);
            wall.setTranslateY(0);
            walls.add(wall);

            Wall secondWall = new Wall(600 - enter - height);
            secondWall.setTranslateX(i * 350 + 600);
            secondWall.setTranslateY(height + enter);
            walls.add(secondWall);
            gameRoot.getChildren().addAll(wall, secondWall);
        }
        gameRoot.getChildren().add(dragonSprite);
        scoreLabel.setFont(Font.font("Cambria", 32));
        appRoot.getChildren().addAll(gameRoot, scoreLabel);
        return appRoot;
    }

    public void update() {
        dragonSprite.changeSprite();
        if (dragonSprite.velocity.getY() < 5) {
            dragonSprite.velocity = dragonSprite.velocity.add(0, 1);
        }

        dragonSprite.moveX((int) dragonSprite.velocity.getX());
        dragonSprite.moveY((int) dragonSprite.velocity.getY());
        scoreLabel.setText("Score: " + score);

        dragonSprite.translateXProperty().addListener((ovs, oldVal, newVal) ->{
            int offset = newVal.intValue();
            if(offset > 200){
                gameRoot.setLayoutX(-(offset - 200));
            }
        });
    }

    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(createContent());
        scene.setOnMouseClicked(event -> dragonSprite.jump());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxHeight(600);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
}
