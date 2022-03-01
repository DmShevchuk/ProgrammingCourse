package data;

import collection.CollectionManager;
import collection.Dragon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.CommandLine;


public class ParserJSON {
    private final static JSONParser PARSER = new JSONParser();
    private final static HashMap<String, Function<Pair, Pair>> CHECKER = new HashMap<>();
    private final static int ALL_FIELDS_ADDED = 7;


    public static void parse(String json) throws ParseException, NoSuchFieldException {
        mapInit();

        JSONObject rootJsonObject = (JSONObject) PARSER.parse(json);
        //Получение массива с драконами из JSON-файла
        JSONArray dragonJSONArray = (JSONArray) rootJsonObject.get("dragons");

        Field[] dragonFields = Dragon.class.getDeclaredFields();

        for (Object dragon : dragonJSONArray) {
            ArrayList<Object> fieldValues = new ArrayList<>();

            //JSON-объект, который хранит значение полей дракона
            JSONObject d = (JSONObject) dragon;

            for (Field field : dragonFields) {
                String fieldName = field.getName();

                if (fieldName.equals("id") || fieldName.equals("creationDate")) {
                    continue;
                }

                if (fieldName.equals("coordinates")) {
                    JSONObject coordinates = (JSONObject) d.get(field.getName());

                    Object xCoord = coordinates.get("x");
                    Object yCoord = coordinates.get("y");

                    Pair<Boolean, ?> response = FieldChecker.
                            checkCoordinates(new Pair<>(field, new Object[]{xCoord, yCoord}));

                    if (response.getFirst()) {
                        fieldValues.add(response.getSecond());
                    }

                } else {
                    Object obj = d.get(fieldName);
                    Function<Pair, Pair> func = CHECKER.get(fieldName);
                    Pair<Boolean, ?> response = func.apply(new Pair(field, obj));

                    if (response.getFirst()) {
                        fieldValues.add(response.getSecond());
                    }
                }
            }

            if (fieldValues.size() == ALL_FIELDS_ADDED) {
                Dragon dr = CollectionManager.createNewDragon(fieldValues);
                CollectionManager.addDragon(dr);
            } else {
                CommandLine.errorOut("Дракон не добавлен в коллекцию!");
            }

        }
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
