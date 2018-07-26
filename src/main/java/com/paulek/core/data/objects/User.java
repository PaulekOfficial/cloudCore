package com.paulek.core.data.objects;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulek.core.utils.consoleLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    private File file;

    private JsonObject jsonObject;

    private UUID uuid;
    private String displayname;
    private long last_login;
    private boolean god;
    private HashMap<String, Location> home;
    private boolean fly;
    private int gamemode;
    private String skin;
    private Long skin_senility;
    private boolean skinset_manually;
    private String alias;
    private String signature;
    private Location last_stay;
    private boolean muted;
    private Long muted_to;
    private String ipAddres;
    private HashMap<String, Long> kit;
    private boolean vanish;
    private boolean socialspy;
    private boolean afk;
    private boolean tptoogle;

    public User(Player player){
        this.uuid = player.getUniqueId();
        this.displayname = player.getDisplayName();
        this.last_login = System.currentTimeMillis();
        this.god = false;
        this.home = new HashMap<>();
        this.fly = false;
        this.gamemode = player.getGameMode().getValue();
        this.skin = "";
        this.signature = "";
        this.skin_senility = (long)0;
        this.skinset_manually = false;
        this.alias = "";
        this.last_stay = player.getLocation();
        this.muted = false;
        this.muted_to = (long)0;

        String ip = player.getAddress().toString();
        String[] spilt_a = ip.split("/");
        String[] split_b = spilt_a[1].split(":");

        this.ipAddres =  split_b[0];
        this.kit = new HashMap<>();
        this.vanish = false;
        this.socialspy = false;
        this.afk = false;
        this.tptoogle = false;

        this.saveUser();
        this.loadFile();
    }

    private void loadFile(){

        this.file = new File("./plugins/clCore/users/", this.uuid.toString() + ".json");

        if(!file.exists()){
            if(!file.getParentFile().exists()) file.getParentFile().mkdir();

            consoleLog.info("Created user " + displayname + " file!");

            saveUser();
            saveFile();

        } else {
            JsonParser jsonParser = new JsonParser();

            try{

                Object parsed = jsonParser.parse(new FileReader(file));

                jsonObject = (JsonObject)parsed;

                JsonObject last = (JsonObject) jsonObject.get("last_stay");

                if(last != null) {
                    Location location_last = new Location(Bukkit.getWorld(last.get("world").getAsString()), last.get("x").getAsInt(), last.get("y").getAsInt(), last.get("z").getAsInt());

                    location_last.setYaw(last.get("yaw").getAsFloat());

                    last_stay = location_last;
                }

                this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
                this.displayname = jsonObject.get("displayname").getAsString();
                this.last_login = jsonObject.get("last_login").getAsLong();
                this.god = jsonObject.get("god").getAsBoolean();
                this.home = new HashMap<String, Location>();
                try {
                    JsonObject home = jsonObject.get("home").getAsJsonObject();

                    Gson gson = new Gson();

                    HashMap<String, Home> objects = new HashMap<String, Home>();

                    for(Map.Entry<String, JsonElement> entry : home.entrySet()){

                        Home home_obj = gson.fromJson(entry.getValue(), Home.class);
                        objects.put(entry.getKey(), home_obj);

                    }

                    for(String s : objects.keySet()){

                        Home obj = objects.get(s);

                        Location location = new Location(Bukkit.getWorld(obj.getWorld()), obj.getX(), obj.getY(), obj.getZ());
                        location.setYaw(obj.getYaw());

                        this.home.put(s, location);

                    }

                    home = null;
                    gson = null;
                    objects = null;

                } catch (Exception e){

                }

                this.fly = jsonObject.get("fly").getAsBoolean();
                this.gamemode = jsonObject.get("gamemode").getAsInt();
                this.skin = jsonObject.get("skin").getAsString();
                this.skin_senility = jsonObject.get("skin_senility").getAsLong();
                this.signature = jsonObject.get("signature").getAsString();
                this.skinset_manually = jsonObject.get("skinset_manually").getAsBoolean();
                this.alias = jsonObject.get("alias").getAsString();
                this.muted = jsonObject.get("muted").getAsBoolean();
                this.muted_to = jsonObject.get("muted_to").getAsLong();
                this.kit = null; //TODO kit system
                this.vanish = jsonObject.get("vanish").getAsBoolean();
                this.socialspy = jsonObject.get("socialspy").getAsBoolean();
                this.afk = jsonObject.get("afk").getAsBoolean();
                this.tptoogle = jsonObject.get("tptoogle").getAsBoolean();

                consoleLog.info("Loaded player " + jsonObject.get("displayname") + " file!");

            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public void saveUser(){

        if(jsonObject != null) jsonObject = null;

        jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", uuid.toString());
        jsonObject.addProperty("displayname", displayname);
        jsonObject.addProperty("last_login", last_login);
        jsonObject.addProperty("god", god);

        if(home != null && (home.size() >= 1)){

            JsonObject jsonObject_home = new JsonObject();

            for(String s : home.keySet()){

                JsonObject object = new JsonObject();

                Location location = home.get(s);

                String world = location.getWorld().getName();

                double x = location.getX();
                double y = location.getY();
                double z = location.getZ();
                double yaw = location.getYaw();

                object.addProperty("world", world);
                object.addProperty("x", x);
                object.addProperty("y", y);
                object.addProperty("z", z);
                object.addProperty("yaw", yaw);

                jsonObject_home.add(s, object);

            }

            jsonObject.add("home", jsonObject_home);

        } else {
            jsonObject.addProperty("home", "");
        }

        jsonObject.addProperty("fly", fly);
        jsonObject.addProperty("gamemode", gamemode);
        jsonObject.addProperty("skin", skin);
        jsonObject.addProperty("skin_senility", skin_senility);
        jsonObject.addProperty("skinset_manually", skinset_manually);
        jsonObject.addProperty("signature", signature);
        jsonObject.addProperty("alias", alias);

        if(last_stay != null){

            String world = last_stay.getWorld().getName();

            double x = last_stay.getX();
            double y = last_stay.getY();
            double z = last_stay.getZ();
            double yaw = last_stay.getYaw();

            JsonObject jsonObject_loc = new JsonObject();
            jsonObject_loc.addProperty("world", world);
            jsonObject_loc.addProperty("x", x);
            jsonObject_loc.addProperty("y", y);
            jsonObject_loc.addProperty("z", z);
            jsonObject_loc.addProperty("yaw", yaw);

            jsonObject.add("last_stay", jsonObject_loc);

        } else {
            jsonObject.addProperty("last_stay", "");
        }

        jsonObject.addProperty("muted", muted);
        jsonObject.addProperty("muted_to", muted_to);
        jsonObject.addProperty("ipAddres", ipAddres);

        if(kit != null && (kit.size() >= 1)){

            JsonObject jsonObject_kit = new JsonObject();

            for(String s : kit.keySet()){

                JsonObject object = new JsonObject();

                long l = kit.get(s);

                jsonObject_kit.addProperty(s, l);

            }

            jsonObject.add("kit", jsonObject_kit);

        } else {
            jsonObject.addProperty("kit", "");
        }

        jsonObject.addProperty("vanish", vanish);
        jsonObject.addProperty("socialspy", socialspy);
        jsonObject.addProperty("afk", afk);
        jsonObject.addProperty("tptoogle", tptoogle);

    }

    public void saveFile(){

        try {

            FileWriter fileWriter = new FileWriter(file);

            try{
                fileWriter.write(jsonObject.toString());
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                fileWriter.flush();
                fileWriter.close();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public HashMap<String, Location> getHome() {
        return home;
    }

    public void setHome(HashMap<String, Location> home) {
        this.home = home;
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Location getLast_stay() {
        return last_stay;
    }

    public void setLast_stay(Location last_stay) {
        this.last_stay = last_stay;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public Long getMuted_to() {
        return muted_to;
    }

    public void setMuted_to(Long muted_to) {
        this.muted_to = muted_to;
    }

    public String getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(String ipAddres) {
        this.ipAddres = ipAddres;
    }

    public HashMap<String, Long> getKit() {
        return kit;
    }

    public void setKit(HashMap<String, Long> kit) {
        this.kit = kit;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean isSocialspy() {
        return socialspy;
    }

    public void setSocialspy(boolean socialspy) {
        this.socialspy = socialspy;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
    }

    public Long getSkin_senility() {
        return skin_senility;
    }

    public void setSkin_senility(Long skin_senility) {
        this.skin_senility = skin_senility;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isSkinset_manually() {
        return skinset_manually;
    }

    public void setSkinset_manually(boolean skinset_manually) {
        this.skinset_manually = skinset_manually;
    }

    public boolean isTptoogle() {
        return tptoogle;
    }

    public void setTptoogle(boolean tptoogle) {
        this.tptoogle = tptoogle;
    }
}
