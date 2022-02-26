package collection;

import utils.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class CollectionManager {
    private static LinkedList<Dragon> COLLECTION = new LinkedList<>();
    private static Integer currentID = 0;
    private static LocalDate currentDate;

    public CollectionManager() {
    }

    private void nextID() {
        currentID++;
    }

    private void refreshDate() {
        currentDate = LocalDate.now();
    }

    public void addDragon(ArrayList<Object> fields) {
        nextID();
        refreshDate();

        Integer id = currentID;
        String name = (String) fields.get(0);
        Coordinates coordinates = (Coordinates) fields.get(1);
        LocalDate creationDate = currentDate;

        Integer age = null;
        if (fields.get(2) != null) {
            age = (Integer) fields.get(2);
        }

        Long weight = (Long) fields.get(3);
        Boolean speaking = (Boolean) fields.get(4);

        DragonType type = null;
        if (fields.get(5) != null) {
            type = (DragonType) fields.get(5);
        }

        DragonHead head = (DragonHead) fields.get(6);

        COLLECTION.add(new Dragon(id, name, coordinates, creationDate, age, weight, speaking, type, head));

    }

    public static String getInfo() {
        return String.format("Тип коллекции: %s\nВ коллекции содержаться объекты: %s\nДата инициализации: %s\nКоличество элементов: %d",
                COLLECTION.getClass(), Dragon.class, currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), COLLECTION.size());
    }

    public LinkedList<Dragon> getCOLLECTION() {
        return (LinkedList<Dragon>) COLLECTION.clone();
    }

    public static int getCollectionSize() {
        return COLLECTION.size();
    }

    public static void removeFirst() {
        COLLECTION.removeFirst();
    }

    public static void removeByHead(DragonHead head) {
        COLLECTION.removeIf(dragon -> dragon.getHead().getSize() == head.getSize());
    }

    public static String sortByWeight() {
        COLLECTION.sort(Comparator.comparing(Dragon::getWeight).reversed());
        String toReturn = "";
        for (Dragon dragon : COLLECTION) {
            toReturn += String.format("%s: вес %d кг.\n", dragon.getName(), dragon.getWeight());
        }
        return toReturn.strip();
    }


    public static String clearCollection() {
        COLLECTION.clear();
        return "Коллекция успешно очищена!";

    }

    public static boolean checkExistingID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static void deleteElementByID(Integer id) {
        int idx;
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                idx = COLLECTION.indexOf(dragon);
                COLLECTION.remove(idx);
                break;
            }
        }
    }

    public static String getElementByID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return dragon.toString();
            }
        }
        return "";
    }

    public static Integer getMinID() {
        int minID = Integer.MAX_VALUE;
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId() < minID) {
                minID = dragon.getId();
            }
        }
        return minID;
    }

    // Сортировка коллекции по имени драконов
    private void sortCollection() {
        COLLECTION.sort(Comparator.naturalOrder());
    }

    public static String collectionToString() {
        String toReturn = "";
        for (Dragon d : COLLECTION) {
            toReturn += d.toString() + "\n";
        }
        return toReturn.strip().length() == 0 ? "Коллекция драконов пуста!" : toReturn.strip();
    }

}
