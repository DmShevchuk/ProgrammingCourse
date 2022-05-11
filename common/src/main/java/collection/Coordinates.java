package collection;

import java.io.Serializable;

/**
 * Координаты являются полем {@link collection.Dragon}
 */
public class Coordinates implements Serializable {
    private final Double x; //Значение поля должно быть больше -972, Поле не может быть null

    private final Double y; //Значение поля должно быть больше -290, Поле не может быть null

    public Coordinates(Double x, Double y) {
        this.x = round(x, 2);
        this.y = round(y, 2);
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
