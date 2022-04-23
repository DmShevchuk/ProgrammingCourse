package commands;

import collection.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import data.CustomDragonSerializer;
import data.FileManager;
import utils.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Save extends Command implements UsesCollectionManager {
    private final String DEFAULT_FILE_NAME = "collection-out.json";

    public Save(CommandLine commandLine) {
        super("save", "|| save collection to file", 0, commandLine);
    }

    @Override
    public void execute() {
        FileManager fileManager = new FileManager(commandLine);

        if (!fileManager.canWrite(DEFAULT_FILE_NAME)) {
            commandLine.errorOut("The current user does not have permission to write to the file "
                    + DEFAULT_FILE_NAME);
            return;
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
            commandLine.successOut("Коллекция успешно записана в файл " + DEFAULT_FILE_NAME + "!");
        } catch (JsonProcessingException | FileNotFoundException e) {
            commandLine.errorOut("Невозможно записать коллекцию в файл!");
        }

    }
}
