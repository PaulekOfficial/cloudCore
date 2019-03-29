package com.paulek.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;

public class LocationDeserializer extends StdDeserializer<Location> {


    public LocationDeserializer() {
        this(null);
    }

    public LocationDeserializer(Class<Location> t) {
        super(t);
    }

    @Override
    public Location deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode worldNode = node.get("world");
        World world = Bukkit.getWorld(worldNode.asText());
        JsonNode xNode = node.get("x");
        Double x = xNode.asDouble();
        JsonNode yNode = node.get("y");
        Double y = yNode.asDouble();
        JsonNode zNode = node.get("z");
        Double z = zNode.asDouble();
        JsonNode yawNode = node.get("yaw");
        Float yaw = yawNode.floatValue();
        JsonNode pitchNode = node.get("pitch");
        Float pitch = pitchNode.floatValue();
        return new Location(world, x, y, z, yaw, pitch);
    }
}
