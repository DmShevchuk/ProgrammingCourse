package data;

import collection.CollectionManager;
import collection.Coordinates;
import collection.Dragon;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import exceptions.FieldValueException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.CommandLine;


public class ParserJSON {
    private final static JSONParser PARSER = new JSONParser();
    private final static HashMap<String, Function<Object, ?>> CONVERTER = new HashMap<>();
    private static final CollectionManager MANAGER = new CollectionManager();
    private final static int ALL_FIELDS_ADDED = 7;


    public static void parse(String json) throws ParseException, NoSuchFieldException, FieldValueException, NoSuchMethodException {
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

                    boolean answerX = FieldValidator.checkField(Coordinates.class.getDeclaredField("x"), xCoord);
                    boolean answerY = FieldValidator.checkField(Coordinates.class.getDeclaredField("y"), yCoord);

                    if (answerX && answerY) {
                        fieldValues.add(FieldConverter.parseCoordinate(xCoord, yCoord));
                    }

                } else {
                    Object value = d.get(fieldName);
                    boolean result = FieldValidator.checkField(field, value);

                    if (result && value != null) {
                        Function<Object, ?> func = CONVERTER.get(fieldName);
                        fieldValues.add(func.apply(value));
                    } else if (result) {
                        fieldValues.add(null);
                    }

                }
            }

            if (fieldValues.size() == ALL_FIELDS_ADDED) {
                MANAGER.addDragon(fieldValues);
            } else {
                CommandLine.outLn("Дракон не добавлен в коллекцию!");
            }

        }
    }

    private static void mapInit() {
        CONVERTER.put("name", FieldConverter::parseName);
        CONVERTER.put("age", FieldConverter::parseAge);
        CONVERTER.put("weight", FieldConverter::parseWeight);
        CONVERTER.put("speaking", FieldConverter::parseSpeaking);
        CONVERTER.put("type", FieldConverter::parseType);
        CONVERTER.put("head", FieldConverter::parseHead);
    }
}
