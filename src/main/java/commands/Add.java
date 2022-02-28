package commands;

import collection.CollectionManager;
import collection.Dragon;
import data.FieldChecker;
import data.Pair;
import utils.CommandLine;
import utils.ElementReadMode;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class Add extends Command {
    private static final LinkedHashMap<String, String> prefixes = new LinkedHashMap<>();
    private static Iterator<Map.Entry<String, String>> ITERATOR;
    private static String currentField;
    private static ArrayList<Object> fieldValues = new ArrayList<>();
    private final static int ALL_FIELDS_ADDED = 7;
    private final static HashMap<String, Function<Pair, Pair>> CHECKER = new HashMap<>();

    public Add() {
        super("add {element} : добавить новый элемент в коллекцию", "add", 0);

        prefixes.put("name", "Введите имя дракона:");
        prefixes.put("coordinates", "Укажите координаты дракона (пример - 10.5 15.9):");
        prefixes.put("age", "Введите возраст:");
        prefixes.put("weight", "Введите вес дракона:");
        prefixes.put("speaking", "Дракон говорит? (true/false):");
        prefixes.put("type", "Введите тип дракона (WATER, UNDERGROUND, AIR, FIRE):");
        prefixes.put("head", "Введите размер головы дракона:");

        mapInit();
    }

    @Override
    public void execute() {
        CommandLine.outLn("Добавление дракона в коллекцию (null == пустая строка)");
        CommandLine.setElementMode(ElementReadMode.ELEMENT_ADD);
        addInit();
    }

    public static void addInit() {
        fieldValues.clear();
        ITERATOR = prefixes.entrySet().iterator();
        nextField();
    }

    public static void addValue(String value) throws NoSuchFieldException {
        Field field = Dragon.class.getDeclaredField(currentField);

        if (value.equals("")) {
            value = null;
        }

        Pair<Boolean, ?> response = new Pair<>(Boolean.FALSE, null);

        if (currentField.equals("coordinates")) {
            try {
                String[] coords = value.split("\\s");
                if (coords.length == 2) {
                    response = FieldChecker.checkCoordinates(new Pair(field, new Object[]{coords[0], coords[1]}));
                }
            } catch (Exception ignored) {
            }
        } else {
            Function<Pair, Pair> func = CHECKER.get(currentField);
            response = func.apply(new Pair(field, value));
        }

        if (response.getFirst()) {
            fieldValues.add(response.getSecond());
            if (fieldValues.size() != ALL_FIELDS_ADDED) {
                nextField();
            }
        } else {
            CommandLine.outLn(String.format("Невозможно получить значение поля %s из %s!", currentField, value));
        }

        if (fieldValues.size() == ALL_FIELDS_ADDED) {
            if (CommandLine.getElementMode() == ElementReadMode.ELEMENT_ADD) {
                Dragon d = CollectionManager.createNewDragon(fieldValues);
                CollectionManager.addDragon(d);
                CommandLine.outLn("Дракон добавлен в коллекцию!");
            } else if (CommandLine.getElementMode() == ElementReadMode.ELEMENT_UPDATE) {
                // InputMode.ELEMENT_UPDATE
                UpdateId.update(fieldValues);
            } else {
                AddIfMax.compare(fieldValues);
            }
            //Возвращение к стандартному пользовательскому вводу
            CommandLine.setElementMode(ElementReadMode.STANDARD);
            CommandLine.setUserInputPrefix(">>");
            fieldValues.clear();
        }

    }

    private static void nextField() {
        currentField = ITERATOR.next().getKey();
        CommandLine.setUserInputPrefix(prefixes.get(currentField));
    }

    private static void mapInit() {
        CHECKER.put("name", FieldChecker::checkName);
        CHECKER.put("age", FieldChecker::checkAge);
        CHECKER.put("weight", FieldChecker::checkWeight);
        CHECKER.put("speaking", FieldChecker::checkSpeaking);
        CHECKER.put("type", FieldChecker::checkType);
        CHECKER.put("head", FieldChecker::checkHead);
    }
}
