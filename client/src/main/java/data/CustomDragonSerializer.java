package data;

import collection.Dragon;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomDragonSerializer extends StdSerializer<Dragon> {

    public CustomDragonSerializer() {
        this(null);
    }

    public CustomDragonSerializer(Class<Dragon> t) {
        super(t);
    }

    @Override
    public void serialize(
            Dragon dragon, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", dragon.getId());
        jsonGenerator.writeStringField("name", dragon.getName());

        jsonGenerator.writeFieldName("coordinates");
        double[] array = {dragon.getCoordinates().getX(), dragon.getCoordinates().getY()};
        jsonGenerator.writeArray(array, 0, 2);

        jsonGenerator.writeStringField("creationDate", dragon.getCreationDate().toString());
        jsonGenerator.writeNumberField("age", dragon.getAge());
        jsonGenerator.writeNumberField("weight", dragon.getWeight());
        jsonGenerator.writeBooleanField("speaking", dragon.getSpeaking());
        jsonGenerator.writeStringField("type", dragon.getType().toString());
        jsonGenerator.writeNumberField("head", dragon.getHead().getSize());
        jsonGenerator.writeEndObject();
    }
}
