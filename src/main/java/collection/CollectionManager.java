package collection;

import utils.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;


public class CollectionManager {
    private static LinkedList<Dragon> COLLECTION = new LinkedList<>();
    private static Integer currentID = 0;
    private static LocalDate currentDate;

    public CollectionManager() {
    }

    private static void nextID() {
        currentID++;
    }

    private static void refreshDate() {
        currentDate = LocalDate.now();
    }

    public static Dragon createNewDragon(ArrayList<Object> fields) {
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

        return new Dragon(id, name, coordinates, creationDate, age, weight, speaking, type, head);
    }

    public static void addDragon(Dragon dragon) {
        COLLECTION.add(dragon);
        sortCollection();
    }

    public static String getInfo() {
        return String.format("Тип коллекции: %s\nВ коллекции содержаться объекты: %s\nДата инициализации: %s\nКоличество элементов: %d",
                COLLECTION.getClass(), Dragon.class, currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), COLLECTION.size());
    }

    public static LinkedList<Dragon> getCOLLECTION() {
        /** Коллекцию можно получить только из {@link commands.Save}*/
        StackTraceElement[] tracer;
        tracer = new Throwable().getStackTrace();
        if (tracer[1].getClassName().equals("commands.Save")) {
            return (LinkedList<Dragon>) COLLECTION.clone();
        }
        return null;
    }

    public static int getCollectionSize() {
        return COLLECTION.size();
    }

    public static void removeFirst() {
        COLLECTION.removeFirst();
    }

    public static void removeByHead(DragonHead head) {
        COLLECTION.removeIf(dragon -> dragon.getHead().getSize() == head.getSize());
        sortCollection();
    }

    public static String sortByWeight() {
        COLLECTION.sort(Comparator.comparing(Dragon::getWeight).reversed());
        String toReturn = "";
        for (Dragon dragon : COLLECTION) {
            toReturn += String.format("%s: вес %d кг.\n", dragon.getName(), dragon.getWeight());
        }
        // Приведение коллекцию к стандартному порядку (сортировка оп имени)
        sortCollection();

        return toReturn.strip();
    }


    public static String clearCollection() {
        COLLECTION.clear();
        return "Коллекция успешно очищена!";
    }

    public static Dragon getMaxElement() {
        sortCollection();
        return COLLECTION.getLast();
    }

    public static boolean checkExistingID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return true;
            }
        }

        CommandLine.outLn(String.format("Не существует id=%d!", id));
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
        sortCollection();
    }

    public static Dragon getElementByID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return dragon;
            }
        }
        return null;
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

    public static void updateElementById(Integer id, ArrayList<Object> fields) {
        Dragon dragon = getElementByID(id);

        dragon.setName((String) fields.get(0));
        dragon.setCoordinates((Coordinates) fields.get(1));

        if (fields.get(2) == null) {
            dragon.setAge(null);
        } else {
            dragon.setAge((Integer) fields.get(2));
        }

        dragon.setWeight((Long) fields.get(3));
        dragon.setSpeaking((Boolean) fields.get(4));

        if (fields.get(5) == null) {
            dragon.setType(null);
        } else {
            dragon.setType((DragonType) fields.get(5));
        }

        dragon.setHead((DragonHead) fields.get(6));
        sortCollection();
    }

    // Сортировка коллекции по возрасту драконов
    private static void sortCollection() {
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
