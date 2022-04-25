package data;

import collection.Dragon;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.LinkedList;


public class ParserJSON {
    public ParserJSON() {}

    public LinkedList<Dragon> parse(String jsonFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRoot = objectMapper.readTree(jsonFile);

        JsonNode jsonNodeDragons = jsonNodeRoot.get("dragons");
        SimpleModule module =
                new SimpleModule("CustomDragonDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Dragon.class, new CustomDragonDeserializer());
        objectMapper.registerModule(module);

        FieldChecker fieldChecker = new FieldChecker();

        LinkedList<Dragon> dragonList = new LinkedList<>();

        for (int i = 0; i < jsonNodeDragons.size(); i++) {
            String dr = jsonNodeDragons.get(i).toString();

            try {
                Dragon dragon = objectMapper.readValue(dr, Dragon.class);

                if (fieldChecker.checkId(dragon.getId()).getFirst() &&
                        fieldChecker.checkName(dragon.getName()).getFirst() &&
                        fieldChecker.checkCoordinates(dragon.getCoordinates().toString()).getFirst() &&
                        fieldChecker.checkAge(dragon.getAge()).getFirst() &&
                        fieldChecker.checkWeight(dragon.getWeight()).getFirst() &&
                        fieldChecker.checkSpeaking(dragon.getSpeaking()).getFirst() &&
                        fieldChecker.checkType(dragon.getType()).getFirst() &&
                        fieldChecker.checkHead(dragon.getHead()).getFirst()
                ) {
                    dragonList.add(dragon);
                }
            } catch (NullPointerException | IOException | DateTimeException e) {
                System.out.println(e.getMessage());
            }
        }
        return dragonList;
    }
}
