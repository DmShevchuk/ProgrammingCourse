package collection;

import java.time.LocalDate;
import java.util.*;


/**
 * Класс для взаимодействия с коллекцией типа {@link collection.Dragon}
 * Singleton
 */
public class CollectionManager {
    private static CollectionManager instance;
    private LocalDate initializationDate = LocalDate.now();
    private LinkedList<Dragon> COLLECTION = new LinkedList<>();
    private Integer currentID = 1;

    private static LocalDate currentDate;

    private CollectionManager() {
    }

    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
            return instance;
        }
        return instance;
    }

    public void collectionInit(List<Dragon> dragonList) {
        if (dragonList != null) COLLECTION = (LinkedList<Dragon>) dragonList;
        sortCollection();
    }

    private void nextID() {
        while (checkExistingID(currentID)) {
            currentID++;
        }
    }

    private void refreshDate() {
        currentDate = LocalDate.now();
    }

    public void addDragon(Dragon dragon) {
        COLLECTION.add(dragon);
        sortCollection();
    }

    public void addDragon(Dragon.Builder builder) {
        nextID();
        refreshDate();

        builder.setId(currentID);
        builder.setCreationDate(currentDate);

        addDragon(builder.build());
    }

    //Получить информацию о хранящейся коллекции
    public String getInfo() {
        return String.format("""
                        Collection type: %s
                        The collection contains objects: %s
                        Initialization date: %s
                        Amount of elements: %d""",
                COLLECTION.getClass(), Dragon.class, initializationDate, COLLECTION.size());
    }

    public LinkedList<Dragon> getCOLLECTION() {
        return (LinkedList<Dragon>) COLLECTION.clone();
    }

    public int getCollectionSize() {
        return COLLECTION.size();
    }

    public void removeFirst() {
        COLLECTION.removeFirst();
    }

    //Удалить из коллекции элементы с указанным размером головы
    public void removeByHead(DragonHead head) {
        COLLECTION.removeIf(dragon -> dragon.getHead().getSize() == head.getSize());
        sortCollection();
    }

    public String sortByWeight() {
        COLLECTION.sort(Comparator.comparing(Dragon::getWeight).reversed());
        String toReturn = "";
        for (Dragon dragon : COLLECTION) {
            toReturn += String.format("%s: вес %d кг.\n", dragon.getName(), dragon.getWeight());
        }
        // Приведение коллекцию к стандартному порядку (сортировка оп имени)
        sortCollection();

        return toReturn.strip();
    }


    public String clearCollection() {
        COLLECTION.clear();
        return "Collection successfully cleared!";
    }

    public Dragon getMaxElement() {
        sortCollection();
        return COLLECTION.getLast();
    }

    public boolean checkExistingID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return true;
            }
        }

        //CommandLine.errorOut(String.format("Does not exist id=%d!", id));
        return false;
    }

    public void deleteElementByID(Integer id) {
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

    public Dragon getElementByID(Integer id) {
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId().equals(id)) {
                return dragon;
            }
        }
        return null;
    }

    public Integer getMinID() {
        int minID = Integer.MAX_VALUE;
        for (Dragon dragon : COLLECTION) {
            if (dragon.getId() < minID) {
                minID = dragon.getId();
            }
        }
        return minID;
    }

    public void updateElementById(Integer id, Dragon d) {
        Dragon dragon = getElementByID(id);

        dragon.setName(d.getName());
        dragon.setCoordinates(d.getCoordinates());
        dragon.setAge(d.getAge());
        dragon.setWeight(d.getWeight());
        dragon.setSpeaking(d.getSpeaking());
        dragon.setType(d.getType());
        dragon.setHead(d.getHead());

        sortCollection();
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public Dragon getElementByIndex(int idx) {
        if (idx < COLLECTION.size()) return COLLECTION.get(idx);
        return null;
    }


    // Сортировка коллекции по возрасту драконов
    public void sortCollection() {
        COLLECTION.sort(Comparator.naturalOrder());
    }

    public String collectionToString() {
        String toReturn = "";
        for (Dragon d : COLLECTION) {
            toReturn += d.toString() + "\n";
        }
        return toReturn.strip().length() == 0 ? "The dragon collection is empty!" : toReturn.strip();
    }

}
