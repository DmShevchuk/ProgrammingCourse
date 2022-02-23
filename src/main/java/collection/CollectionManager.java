package collection;

import utils.CommandLine;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public LinkedList<Dragon> getCOLLECTION() {
        return (LinkedList<Dragon>) COLLECTION.clone();
    }

    public void collectionToString(){
        for(Dragon d: COLLECTION){
            CommandLine.outLn(d.toString());
        }
    }
}
