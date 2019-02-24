package com.paulek.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.paulek.core.basic.Skin;

import java.io.IOException;

public class SkinDeserializer extends StdDeserializer<Skin> {

    public SkinDeserializer(){
        this(null);
    }

    public SkinDeserializer(Class<Skin> t){
        super(t);
    }


    @Override
    public Skin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode nameNode = node.get("name");
        JsonNode valueNode = node.get("value");
        JsonNode signature = node.get("signature");
        JsonNode lastUpdateNode = node.get("lastUpdate");
        return new Skin(nameNode.asText(), valueNode.asText(), signature.asText(), lastUpdateNode.asLong());
    }
}
