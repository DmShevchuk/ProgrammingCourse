package commands.command;

import collection.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import commands.Command;
import data.*;
import interaction.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Save extends Command {
    private final String DEFAULT_FILE_NAME = "collection-out.json";
    private final CollectionManager collectionManager;
    private final Logger logger;

    public Save(CollectionManager collectionManager, Logger logger) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.logger = logger;
    }

    @Override
    public Response execute(Request request) {
        FileManager fileManager = new FileManager();

        if (!fileManager.canWrite(DEFAULT_FILE_NAME)) {
            System.out.println("The current user does not have permission to write to the file "
                    + DEFAULT_FILE_NAME);
            return null;
        }

        File file = new File(DEFAULT_FILE_NAME);
        PrintWriter writer;

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomDragonSerializer",
                        new Version(1, 0, 0, null, null, null));
        module.addSerializer(Dragon.class, new CustomDragonSerializer());
        objectMapper.registerModule(module);

        StringBuilder json = new StringBuilder();

        try {
            writer = new PrintWriter(file);
            for (Dragon d : collectionManager.getCOLLECTION()) {
                String dragonAsString = objectMapper.writeValueAsString(d);
                json.append(dragonAsString).append(",");
            }

            json = new StringBuilder("{\"dragons\": [" + json.toString().replaceAll(",$", "") + "]}");

            writer.write(String.valueOf(json));
            writer.close();
            System.out.println("Collection successfully written to file!");
        } catch (JsonProcessingException | FileNotFoundException e) {
            System.out.println("Невозможно записать коллекцию в файл!");
        }
        return null;
    }
}
