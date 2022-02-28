package commands;

import collection.*;
import utils.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class Save extends Command {
    private final String DEFAULT_FILE_NAME = "collection-out.json";

    public Save() {
        super("save : сохранить коллекцию в файл", "save", 0);
    }

    @Override
    public void execute() {
        HashMap<String, Method> dragonFields = new HashMap<>();
        try {
            dragonFields.put("id", Dragon.class.getMethod("getId"));
            dragonFields.put("name", Dragon.class.getMethod("getName"));
            dragonFields.put("coordinates", Dragon.class.getMethod("getCoordinates"));
            dragonFields.put("creationDate", Dragon.class.getMethod("getCreationDate"));
            dragonFields.put("age", Dragon.class.getMethod("getAge"));
            dragonFields.put("weight", Dragon.class.getMethod("getWeight"));
            dragonFields.put("speaking", Dragon.class.getMethod("getSpeaking"));
            dragonFields.put("type", Dragon.class.getMethod("getType"));
            dragonFields.put("head", Dragon.class.getMethod("getHead"));
        } catch (NoSuchMethodException e) {
        }


        if (CollectionManager.getCollectionSize() == 0) {
            CommandLine.outLn("В коллекции нет элементов, её невозможно записать! ");
            return;
        }

        File file = new File(DEFAULT_FILE_NAME);
        PrintWriter writer;
        String json = "{\n" +
                "  \"dragons\": [";

        try {
            writer = new PrintWriter(file);
            LinkedList<Dragon> collection = CollectionManager.getCOLLECTION();

            for (Dragon dragon : collection) {
                json += "{\n";
                for (Field field : Dragon.class.getDeclaredFields()) {
                    Method method = dragonFields.get(field.getName());
                    Object value = method.invoke(dragon);
                    json += String.format("\"%s\": ", field.getName());

                    if (field.getName().equals("coordinates")) {
                        Coordinates coords = (Coordinates) value;
                        Double x = coords.getX();
                        Double y = coords.getY();
                        json += "{\n \"x\": " + x + ",\n \"y\": " + y + "\n},\n";
                        continue;
                    }

                    if (field.getName().equals("type")) {
                        value = value.toString();
                    }

                    if (field.getName().equals("head")) {
                        value = ((DragonHead) value).getSize();
                    }

                    if (value.getClass() == String.class || value.getClass() == LocalDate.class ||
                            value.getClass() == DragonType.class) {
                        value = String.format("\"%s\"", value);
                    }

                    json += value + ",\n";
                }
                json = json.substring(0, json.length() - 2) + "\n";
                json += "},";
            }

            json = json.substring(0, json.length() - 1);
            json += "  ]\n" +
                    "}";

            writer.write(json);
            writer.close();
            CommandLine.outLn(String.format("Запись коллекции в файл %s была выполнена успешно!", DEFAULT_FILE_NAME));
        } catch (FileNotFoundException | InvocationTargetException | IllegalAccessException e) {
            CommandLine.outLn("Невозможно записать коллекцию в файл " + DEFAULT_FILE_NAME);
        }

    }
}
