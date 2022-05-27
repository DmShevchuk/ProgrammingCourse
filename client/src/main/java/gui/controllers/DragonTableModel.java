package gui.controllers;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

public class DragonTableModel implements Serializable {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Double x;
    @Getter
    @Setter
    private Double y;
    @Getter
    @Setter
    private LocalDate creationDate;
    @Getter
    @Setter
    private Integer age;
    @Getter
    @Setter
    private Long weight;
    @Getter
    @Setter
    private Boolean speaking;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Long headSize;


    public DragonTableModel(Dragon dragon) {
        this.id = dragon.getId();
        this.name = dragon.getName();
        this.x = dragon.getCoordinates().getX();
        this.y = dragon.getCoordinates().getY();
        this.creationDate = dragon.getCreationDate();
        this.age = dragon.getAge();
        this.weight = dragon.getWeight();
        this.speaking = dragon.getSpeaking();
        this.type = dragon.getType().toString();
        this.headSize = dragon.getHead().getSize();
    }
}
