package collection;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс для взаимодействия с коллекцией типа {@link collection.Dragon}
 * Singleton
 */
public class CollectionManager {
    private static CollectionManager instance;
    private final LocalDate initializationDate = LocalDate.now();
    private LinkedList<Dragon> collection = new LinkedList<>();
    private Integer currentId = 1;

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
        if (dragonList != null) collection = (LinkedList<Dragon>) dragonList;
        sortCollection();
    }

    private void nextID() {
        while (checkExistingID(currentId)) {
            currentId++;
        }
    }

    private void refreshDate() {
        currentDate = LocalDate.now();
    }

    public void addDragon(Dragon dragon) {
        collection.add(dragon);
        sortCollection();
    }

    public void addDragon(Dragon.Builder builder) {
        nextID();
        refreshDate();

        builder.setId(currentId);
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
                collection.getClass(), Dragon.class, initializationDate, collection.size());
    }

    public LinkedList<Dragon> getCOLLECTION() {
        return (LinkedList<Dragon>) collection.clone();
    }

    public int getCollectionSize() {
        return collection.size();
    }

    public void removeFirst() {
        collection.removeFirst();
    }

    //Удалить из коллекции элементы с указанным размером головы
    public void removeByHead(Integer ownerId, DragonHead head) {
        collection.removeIf(dragon ->
                (dragon.getHead().getSize() == head.getSize() && ownerId.equals(dragon.getOwnerId())));
        sortCollection();
    }

    public LinkedList<Dragon> sortByWeight() {
        return collection.stream()
                .sorted(Comparator.comparing(Dragon::getWeight))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void clearCollection(Integer ownerId) {
        collection.removeIf(dragon -> ownerId.equals(dragon.getOwnerId()));
    }

    public Dragon getMaxElement() {
        sortCollection();
        return collection.getLast();
    }

    public boolean checkExistingID(Integer id) {
        return collection.stream()
                .filter(dragon -> id.equals(dragon.getId()))
                .findAny()
                .orElse(null) != null;
    }

    public void deleteElementByID(Integer id) {
        collection.removeIf(dragon -> id.equals(dragon.getId()));
    }

    public Dragon getElementByID(Integer id) {
        return collection.stream()
                .filter(dragon -> id.equals(dragon.getId()))
                .findAny()
                .orElse(null);
    }

    public Dragon getMinById() {
        return collection.stream()
                .min(Comparator.comparing(Dragon::getId))
                .orElse(null);
    }

    public Integer getFirstId() {
        return collection.getFirst().getId();
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
        dragon.setOwnerId(d.getOwnerId());

        sortCollection();
    }

    // Сортировка коллекции по весу драконов
    public void sortCollection() {
        collection.sort(Comparator.naturalOrder());
    }

    public String collectionToString() {
        String toReturn = "";

        for (Dragon d : collection) {
            toReturn += d.toString() + "\n";
        }
        return toReturn.strip().length() == 0 ? "The dragon collection is empty!" : toReturn.strip();
    }

}
