package collection;

import annotations.GreaterThan;
import annotations.NotNull;

public class Coordinates {
    @NotNull
    @GreaterThan(minValue = -972)
    private Double x; //Значение поля должно быть больше -972, Поле не может быть null

    @NotNull
    @GreaterThan(minValue = -290)
    private Double y; //Значение поля должно быть больше -290, Поле не может быть null

    public Coordinates(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void changeX(Double delta){
        x += delta;
    }

    public void changeY(Double delta){
        y += delta;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
