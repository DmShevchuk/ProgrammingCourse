package collection;

import annotations.EnumType;
import annotations.GreaterThan;
import annotations.HasLength;
import annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Главный класс, объекты которого хранятся в коллекции
 */
public class Dragon implements Comparable<Dragon> {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, уникальное, генерируется авт
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null
    private Long weight; //Значение поля должно быть больше 0, Поле не может быть null
    private Boolean speaking; //Поле не может быть null
    private DragonType type; //Поле может быть null
    private DragonHead head;

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

    private Dragon(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.age = builder.age;
        this.weight = builder.weight;
        this.speaking = builder.speaking;
        this.type = builder.type;
        this.head = builder.head;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public void setSpeaking(Boolean speaking) {
        this.speaking = speaking;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public void setHead(DragonHead head) {
        this.head = head;
    }

    @Override
    public int compareTo(Dragon o) {
        return this.age.compareTo(o.getAge());
    }

    @Override
    public String toString() {
        return String.format("Dragon {id=%d, name=%s, coordinates=(%s, %s), creationDate=%s, " +
                        "age=%d, weight=%d, speaking=%s, type=%s, head=%s}",
                id, name, coordinates.getX(), coordinates.getY(),
                creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                age, weight, speaking, type, head.getSize());
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

    public static class Builder {
        private Integer id = 0;
        private String name = "";
        private Coordinates coordinates = new Coordinates(10.5, 20.5);
        private LocalDate creationDate = LocalDate.now();
        private Integer age;
        private Long weight = 1000L;
        private Boolean speaking = Boolean.TRUE;
        private DragonType type;
        private DragonHead head = new DragonHead(500);


        public Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCoordinates(Coordinates coords) {
            this.coordinates = coords;
            return this;
        }

        public Builder setCreationDate(LocalDate date) {
            this.creationDate = date;
            return this;
        }

        public Builder setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Builder setWeight(Long weight) {
            this.weight = weight;
            return this;
        }

        public Builder setSpeaking(Boolean speaking) {
            this.speaking = speaking;
            return this;
        }

        public Builder setType(DragonType type) {
            this.type = type;
            return this;
        }

        public Builder setHead(DragonHead head) {
            this.head = head;
            return this;
        }

        public Dragon build() {
            return new Dragon(this);
        }
    }
}