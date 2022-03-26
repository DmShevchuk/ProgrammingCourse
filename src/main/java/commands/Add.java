package commands;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;
import data.FieldChecker;
import data.Pair;
import utils.CommandLine;
import utils.ElementReadMode;

import java.util.*;
import java.util.function.Function;

public class Add extends Command implements UsesCollectionManager {
    private final Map<String, String> prefixes = new LinkedHashMap<>();
    private Iterator<Map.Entry<String, String>> ITERATOR;
    private String currentField;

    // Пользователь может устанавливать все поля, за исключением id, creationDate
    private int ALL_FIELDS_ADDED;
    private final Map<String, Function<Object, Pair>> CHECKER = new HashMap<>();
    private final FieldChecker fieldChecker = new FieldChecker();
    private Dragon.Builder currentDragon;
    private final CommandManager commandManager;

    public Add(CommandLine commandLine, CommandManager commandManager) {
        super("add", "|| add a new element to the collection", 0, commandLine);
        this.commandManager = commandManager;

        prefixes.put("name", "Enter dragon name:");
        prefixes.put("coordinates", "Specify the coordinates of the dragon (ex. - 10.5 15.9):");
        prefixes.put("age", "Enter age:");
        prefixes.put("weight", "Enter the weight of the dragon:");
        prefixes.put("speaking", "The dragon speaks? (true/false):");
        prefixes.put("type", "Enter dragon type (WATER, UNDERGROUND, AIR, FIRE):");
        prefixes.put("head", "Enter the size of the dragon's head:");

        mapInit();
    }

    @Override
    public void execute() {
        commandLine.outLn("Adding a dragon to the collection (empty string -> null)");
        commandLine.setElementMode(ElementReadMode.ELEMENT_ADD);
        addInit();
    }

    public void addInit() {
        currentDragon = new Dragon.Builder();
        ITERATOR = prefixes.entrySet().iterator();
        ALL_FIELDS_ADDED = Dragon.class.getDeclaredFields().length - 2;
        nextField();
    }

    public void addValue(String value) {

        if (value.equals("")) {
            value = null;
        }

        Function<Object, Pair> func = CHECKER.get(currentField);
        Pair<Boolean, ?> response = func.apply(value);

        if (response.getFirst()) {
            switch (currentField) {
                case "name" -> currentDragon.setName((String) response.getSecond());
                case "coordinates" -> currentDragon.setCoordinates((Coordinates) response.getSecond());
                case "age" -> currentDragon.setAge((Integer) response.getSecond());
                case "weight" -> currentDragon.setWeight((Long) response.getSecond());
                case "speaking" -> currentDragon.setSpeaking((Boolean) response.getSecond());
                case "type" -> currentDragon.setType((DragonType) response.getSecond());
                case "head" -> currentDragon.setHead((DragonHead) response.getSecond());
            }
            ALL_FIELDS_ADDED -= 1;
            if (ALL_FIELDS_ADDED != 0) nextField();
        } else {
            commandLine.errorOut(String.format("Unable to get %s from %s!", currentField, value));
        }

        if (ALL_FIELDS_ADDED == 0) {
            if (commandLine.getElementMode() == ElementReadMode.ELEMENT_ADD) {
                collectionManager.addDragon(currentDragon);
                commandLine.successOut("Dragon added to collection!");
            } else if (commandLine.getElementMode() == ElementReadMode.ELEMENT_UPDATE) {
                ((UpdateId)commandManager.getCommand("update_id")).update(currentDragon.build());
            } else {
                ((AddIfMax)commandManager.getCommand("add_if_max")).compare(currentDragon);
            }
            //Возвращение к стандартному пользовательскому вводу
            commandLine.setElementMode(ElementReadMode.STANDARD);
            commandLine.setUserInputPrefix(">>");
        }
    }

    private void nextField() {
        currentField = ITERATOR.next().getKey();
        commandLine.setUserInputPrefix(prefixes.get(currentField));
    }

    private void mapInit() {
        CHECKER.put("name", fieldChecker::checkName);
        CHECKER.put("coordinates", fieldChecker::checkCoordinates);
        CHECKER.put("age", fieldChecker::checkAge);
        CHECKER.put("weight", fieldChecker::checkWeight);
        CHECKER.put("speaking", fieldChecker::checkSpeaking);
        CHECKER.put("type", fieldChecker::checkType);
        CHECKER.put("head", fieldChecker::checkHead);
    }
}
