package gui.game;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class DragonSprite extends Pane {
    public Point2D velocity;
    ImageView rectangle;

    public DragonSprite() {
        rectangle = new ImageView("C:\\Users\\dmitr\\IdeaProjects\\lab_prog\\client\\src\\main\\resources\\game\\dragon.png");
        velocity = new Point2D(0, 0);
        setTranslateY(300);
        setTranslateX(100);
        getChildren().addAll(rectangle);
    }

    public void moveY(int value) {
        // Width: 50
        // Height: 44
        boolean moveDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Wall w : FlappyDragon.walls) {
                if (this.getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (moveDown) {
                        setTranslateY(getTranslateY() + 1);
                        return;
                    }
                    setTranslateY(getTranslateY() - 1);
                    return;
                }

            }
            if (getTranslateY() < 0) {
                setTranslateY(0);
            }
            if (getTranslateY() > 550) {
                setTranslateY(550);
            }
            setTranslateY(getTranslateY() + (moveDown ? 1 : -1));
        }
    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            setTranslateX(getTranslateX() + 1);
            for (Wall w : FlappyDragon.walls) {
                if (getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (getTranslateX() + 50 == w.getTranslateX()) {
                        setTranslateX(getTranslateX() - 1);

                        return;
                    }
                }
                if (getTranslateX() == w.getTranslateX()){
                    FlappyDragon.score++;
                    String musicFile = "C:\\Users\\dmitr\\IdeaProjects\\lab_prog\\client\\src\\main\\resources\\game\\sfx_point.mp3";

                    Media sound = new Media(new File(musicFile).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            }
        }
    }

    public void jump(){
        velocity = new Point2D(3, -15);
        String musicFile = "C:\\Users\\dmitr\\IdeaProjects\\lab_prog\\client\\src\\main\\resources\\game\\sfx_wing.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}