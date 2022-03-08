package commands;

import collection.*;
import data.FileManager;
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
    //Необходимо указывать полный путь до файла!
    private final String DEFAULT_FILE_NAME = "collection-out.json";

    public Save() {
        super("save : save collection to file", "save", 0);
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
            CommandLine.errorOut("The collection has no elements, it cannot be written to!");
            return;
        }

        if(!FileManager.canWrite(DEFAULT_FILE_NAME)){
            CommandLine.errorOut("The current user does not have permission to write to the file "
                    + DEFAULT_FILE_NAME);
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

            CommandLine.successOut(String.format("Write collection to file %s was successful!", DEFAULT_FILE_NAME));

        } catch (FileNotFoundException | InvocationTargetException | IllegalAccessException e) {
            CommandLine.errorOut("Cannot write collection to file " + DEFAULT_FILE_NAME);
        }

    }
}
