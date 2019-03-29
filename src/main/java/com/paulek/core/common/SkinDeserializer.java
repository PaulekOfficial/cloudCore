package com.paulek.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;

import java.io.IOException;
import java.util.Objects;

public class SkinDeserializer extends StdDeserializer<Skin> {

    private Core core;

    public SkinDeserializer(Core core) {
        this(null, core);
    }

    public SkinDeserializer(Class<Skin> t, Core core) {
        super(t);
        this.core = Objects.requireNonNull(core, "Core");
    }


    @Override
    public Skin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode nameNode = node.get("name");
        JsonNode valueNode = node.get("value");
        JsonNode signature = node.get("signature");
        JsonNode lastUpdateNode = node.get("lastUpdate");
        return new Skin(nameNode.asText(), valueNode.asText(), signature.asText(), lastUpdateNode.asLong(), core);
    }
}
