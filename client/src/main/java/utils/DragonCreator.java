package utils;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;
import data.FieldChecker;
import data.Pair;
import exceptions.DragonInputInterruptedException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/*
Класс, отвечающий за создание объекта Dragon из пользовательского ввода
**/

public class DragonCreator {
    private final CommandLine commandLine;
    private int allFieldsAdded;
    private final FieldChecker fieldChecker = new FieldChecker();
    private final Map<String, String> prefixes = new LinkedHashMap<>();
    private String currentField;
    private Dragon.Builder currentDragon;
    private Iterator<Map.Entry<String, String>> iterator;

    public DragonCreator(CommandLine commandLine) {
        this.commandLine = commandLine;

        prefixes.put("name", "Enter dragon name:");
        prefixes.put("coordinates", "Specify the coordinates of the dragon (ex. - 10.5 15.9):");
        prefixes.put("age", "Enter age:");
        prefixes.put("weight", "Enter the weight of the dragon:");
        prefixes.put("speaking", "The dragon speaks? (true/false):");
        prefixes.put("type", "Enter dragon type (WATER, UNDERGROUND, AIR, FIRE):");
        prefixes.put("head", "Enter the size of the dragon's head:");

        creatorInit();
    }

    public Dragon.Builder getNewDragon() throws DragonInputInterruptedException {
        while (true) {
            commandLine.out(prefixes.get(currentField));
            String line = commandLine.getNextLine();
            // Если пользователь не хочет вводить поля, прекращаем создание/обновление дракона
            if ("_quit_".equals(line)) {
                commandLine.setUserInputPrefix(">>");
                throw new DragonInputInterruptedException();
            }
            // Пустая строка -> null
            if ("".equals(line)) {
                line = null;
            }

            Pair<Boolean, ?> result = fieldChecker.checkField(currentField, line);

            if (result.getFirst()) {
                switch (currentField) {
                    case "name" -> currentDragon.setName((String) result.getSecond());
                    case "coordinates" -> currentDragon.setCoordinates((Coordinates) result.getSecond());
                    case "age" -> currentDragon.setAge((Integer) result.getSecond());
                    case "weight" -> currentDragon.setWeight((Long) result.getSecond());
                    case "speaking" -> currentDragon.setSpeaking((Boolean) result.getSecond());
                    case "type" -> currentDragon.setType((DragonType) result.getSecond());
                    case "head" -> currentDragon.setHead((DragonHead) result.getSecond());
                }
                allFieldsAdded -= 1;
                if (allFieldsAdded != 0) nextField();
            } else {
                commandLine.errorOut(String.format("Unable to get %s from %s!", currentField, line));
            }
            // Возврат к обычному вводу пользователя
            if (allFieldsAdded == 0) {
                commandLine.setUserInputPrefix(">>");
                return currentDragon;
            }
        }

    }

    private void creatorInit() {
        currentDragon = new Dragon.Builder();
        iterator = prefixes.entrySet().iterator();
        // Все поля за исключением id, creationDate и ownerId
        allFieldsAdded = Dragon.class.getDeclaredFields().length - 3;
        nextField();
    }

    private void nextField() {
        currentField = iterator.next().getKey();
        commandLine.setUserInputPrefix(prefixes.get(currentField));
    }
}