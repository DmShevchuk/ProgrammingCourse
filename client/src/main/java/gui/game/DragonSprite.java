package gui.game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class DragonSprite extends Pane {
    public Point2D velocity;
    ImageView rectangle;
    private final String[] images = new String[]{"d1.png", "d2.png", "d3.png", "d4.png", "d5.png"};
    private int sprite = 0;
    private int tick = 10;

    public DragonSprite() {
        rectangle = new ImageView(getFilePath(images[sprite]).getAbsolutePath());
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
                if (getTranslateX() == w.getTranslateX()) {
                    FlappyDragon.score++;

                    Media sound = new Media(getFilePath("sfx_point.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            }
        }
    }

    public void jump() {
        velocity = new Point2D(3, -15);

        Media sound = new Media(getFilePath("sfx_wing.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void changeSprite() {
        tick--;
        if (tick == 0) {
            rectangle.setImage(new Image(getFilePath(images[sprite++ % 5]).getAbsolutePath()));
            tick = 10;
        }
    }

    private File getFilePath(String fileName) {
        return new File("client\\src\\main\\resources\\game\\" + fileName);
    }
}