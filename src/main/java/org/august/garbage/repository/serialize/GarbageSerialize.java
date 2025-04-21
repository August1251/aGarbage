package org.august.garbage.repository.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.august.garbage.model.GarbageModel;

import java.io.IOException;

public class GarbageSerialize extends StdSerializer<GarbageModel> {

    public GarbageSerialize(Class t) {
        super(t);
    }

    @Override
    public void serialize(GarbageModel garbageModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("creator", garbageModel.getCreator());
        jsonGenerator.writeStringField("world", garbageModel.getWorld());
        jsonGenerator.writeNumberField("x", garbageModel.getX());
        jsonGenerator.writeNumberField("y", garbageModel.getX());
        jsonGenerator.writeNumberField("z", garbageModel.getX());

        jsonGenerator.writeEndObject();
    }
}