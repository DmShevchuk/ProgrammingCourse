package data;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;


public class CustomDragonDeserializer extends StdDeserializer<Dragon> {

    public CustomDragonDeserializer() {
        this(null);
    }

    public CustomDragonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Dragon deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException,
            NullPointerException {
        Dragon.Builder builder = new Dragon.Builder();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JsonNode idNode = node.get("id");
        builder.setId(idNode.asInt());

        JsonNode nameNode = node.get("name");
        builder.setName(nameNode.asText());

        JsonNode coordNode = node.get("coordinates");
        builder.setCoordinates(new Coordinates(coordNode.get(0).asDouble(), coordNode.get(1).asDouble()));

        JsonNode dateNode = node.get("creationDate");
        builder.setCreationDate(LocalDate.parse(dateNode.asText()));

        JsonNode weightNode = node.get("weight");
        builder.setWeight(weightNode.asLong());

        JsonNode speakingNode = node.get("speaking");
        builder.setSpeaking(speakingNode.asBoolean());

        JsonNode headNode = node.get("head");
        builder.setHead(new DragonHead(headNode.asLong()));

        JsonNode ageNode = node.get("age");
        builder.setAge(ageNode.asInt());

        JsonNode typeNode = node.get("type");
        builder.setType(DragonType.getTypeByString(typeNode.asText()));

        return builder.build();
    }
}