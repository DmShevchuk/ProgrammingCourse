package collection;

import annotations.EnumType;
import annotations.GreaterThan;
import annotations.HasLength;
import annotations.NotNull;

import java.time.LocalDate;

public class Dragon implements Comparable<Dragon> {
    @NotNull
    @GreaterThan
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    @HasLength
    private final String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @GreaterThan
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null

    @NotNull
    @GreaterThan
    private Long weight; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    private final Boolean speaking; //Поле не может быть null

    @NotNull
    @EnumType
    private final DragonType type; //Поле может быть null

    @NotNull
    @GreaterThan
    private final DragonHead head;

    public Dragon(Integer id, String name, Coordinates coordinates, LocalDate creationDate,
                  Integer age, Long weight, Boolean speaking, DragonType type, DragonHead head) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.type = type;
        this.head = head;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getAge() {
        return age;
    }

    public Long getWeight() {
        return weight;
    }

    public Boolean getSpeaking() {
        return speaking;
    }

    public DragonType getType() {
        return type;
    }

    public DragonHead getHead() {
        return head;
    }

    public void changeCoordinates(Double deltaX, Double deltaY) {
        coordinates.changeX(deltaX);
        coordinates.changeY(deltaY);
    }

    public void increaseAge() {
        age++;
    }

    public void increaseWeight(Long delta) {
        if (delta > 0 || (delta < 0 && weight - delta > 0)) {
            weight += delta;
        }
    }

    @Override
    public int compareTo(Dragon o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return String.format("Dragon {id = %d, name = %s, coordinates = %s, creationDate = %s," +
                        "age = %d, weight = %d, speaking = %s, type = %s, head = %s}",
                id, name, coordinates, creationDate, age, weight, speaking, type, head.getSize());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dragon d)) return false;
        if (this == o) return true;
        return this.getId().equals(d.getId());
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}