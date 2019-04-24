package com.paulek.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.Timestamp;
import com.paulek.core.basic.User;
import com.paulek.core.basic.UserSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class UserDeserializer extends StdDeserializer<User> {

    private Core core;

    public UserDeserializer(Core core){
        this(null, Objects.requireNonNull(core, "Core"));
    }

    public UserDeserializer(Class<User> t, Core core){
        super(t);
        this.core = core;
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        User user = new User(core);

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        user.setUuid(UUID.fromString(node.get("uuid").asText()));
        user.setLastAccountName(node.get("lastAccountName").asText());
        user.setSkin(deserializeSkin(node.get("skin")));
        user.setLogoutlocation((node.get("logoutlocation").asText().equalsIgnoreCase("null")) ? null : deserializeLocation(node.get("logoutlocation")));
        user.setLastlocation((node.get("lastlocation").asText().equalsIgnoreCase("null")) ? null : deserializeLocation(node.get("lastlocation")));
        user.setIpAddres((node.get("ipAddres").asText().equalsIgnoreCase("null")) ? null : InetAddress.getByName(node.get("ipAddres").asText()));
        user.setTimestamps((node.get("timestamps").asText().equalsIgnoreCase("null")) ? new HashMap<>() : deserializeTimestamps(node.get("timestamps")));
        user.setAfk(node.get("afk").asBoolean());
        user.setAfkSince(node.get("afkSince").asLong());
        user.setAfkResson(node.get("afkResson").asText());
        user.setHomes((node.get("homes").asText().equalsIgnoreCase("null")) ? new HashMap<>() : deserializeHomes(node.get("homes")));
        user.setLastActivity(node.get("lastActivity").asLong());
        user.setSettings((node.get("settings").asText().equalsIgnoreCase("null")) ? new UserSettings() : deserializeUserSettings(node.get("settings")));

        return user;
    }

    private UserSettings deserializeUserSettings(JsonNode node){

        boolean socialspy = node.get("socialspy").asBoolean();
        boolean vanish = node.get("vanish").asBoolean();
        boolean tptoogle = node.get("tptoogle").asBoolean();
        boolean tps = node.get("tps").asBoolean();

        return new UserSettings(socialspy, vanish, tptoogle, tps);
    }

    private Map<String, Location> deserializeHomes(JsonNode node){

        Map<String, Location> map = new HashMap<>();

        Iterator i = node.fieldNames();

        while(i.hasNext()){

            String field = node.get((String) i.next()).asText();

            JsonNode homeNode = node.get(field);

            String world = homeNode.get("world").asText();
            double x = homeNode.get("x").asDouble();
            double y = homeNode.get("y").asDouble();
            double z = homeNode.get("z").asDouble();
            float yaw = homeNode.get("yaw").asLong();
            float pitch = homeNode.get("pitch").asLong();

            map.put(field, new Location(Bukkit.getWorld(world), x, y ,z , yaw, pitch));

        }

        return map;
    }

    private Map<String, Timestamp> deserializeTimestamps(JsonNode node){

        Map<String, Timestamp> map = new HashMap<>();

        Iterator i = node.fieldNames();

        while(i.hasNext()){

            String field = node.get((String) i.next()).asText();

            JsonNode timestampNode = node.get(field);

            long endTime = timestampNode.get("endTime").asLong();
            String className = timestampNode.get("className").asText();

            map.put(field, new Timestamp(className, endTime));

        }

        return map;
    }

    private Skin deserializeSkin(JsonNode skinNode){
        String skinName = skinNode.get("name").asText();
        String skinValue = skinNode.get("value").asText();
        String skinSignature = skinNode.get("signature").asText();
        long skinLastUpdate = skinNode.get("lastUpdate").asLong();
        return new Skin(skinName, skinValue, skinSignature, skinLastUpdate, core);
    }

    private Location deserializeLocation(JsonNode node){
        JsonNode worldNode = node.get("world");
        World world = Bukkit.getWorld(worldNode.asText());
        JsonNode xNode = node.get("x");
        double x = xNode.asDouble();
        JsonNode yNode = node.get("y");
        double y = yNode.asDouble();
        JsonNode zNode = node.get("z");
        double z = zNode.asDouble();
        JsonNode yawNode = node.get("yaw");
        float yaw = yawNode.floatValue();
        JsonNode pitchNode = node.get("pitch");
        float pitch = pitchNode.floatValue();
        return new Location(world, x, y, z, yaw, pitch);
    }
}
