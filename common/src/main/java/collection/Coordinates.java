package collection;

import java.io.Serializable;

/**
 * Координаты являются полем {@link collection.Dragon}
 */
public class Coordinates implements Serializable {
    private final Double x; //Значение поля должно быть больше -972, Поле не может быть null

    private final Double y; //Значение поля должно быть больше -290, Поле не может быть null

    public Coordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
