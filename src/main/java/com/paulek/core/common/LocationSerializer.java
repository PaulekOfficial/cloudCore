package com.paulek.core.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.Location;

import java.io.IOException;

public class LocationSerializer extends StdSerializer<Location> {

    public LocationSerializer(){
        this(null);
    }

    public LocationSerializer(Class<Location> t){
        super(t);
    }

    @Override
    public void serialize(Location location, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("world", location.getWorld().getName());
        jsonGenerator.writeObjectField("x", location.getX());
        jsonGenerator.writeObjectField("y", location.getY());
        jsonGenerator.writeObjectField("z", location.getZ());
        jsonGenerator.writeObjectField("yaw", location.getYaw());
        jsonGenerator.writeObjectField("pitch", location.getPitch());

        jsonGenerator.writeEndObject();
    }
}
