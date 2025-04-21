package org.august.garbage.repository.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.august.garbage.model.GarbageModel;

import java.io.IOException;

public class GarbageDeserialize extends StdDeserializer<GarbageModel> {

    public GarbageDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public GarbageModel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String creator = jsonNode.get("creator").asText();
        String world = jsonNode.get("world").asText();
        int x = jsonNode.get("x").asInt();
        int y = jsonNode.get("y").asInt();
        int z = jsonNode.get("z").asInt();

        return new GarbageModel(creator, world, x, y, z);
    }
}
