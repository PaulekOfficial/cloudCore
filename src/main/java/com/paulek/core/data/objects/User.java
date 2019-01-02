package com.paulek.core.data.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.net.Inet4Address;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String lastAccountName;
    private Skin skin;
    private Location logoutlocation;
    private Location lastlocation;
    private Inet4Address ipAddres;
    //timestamps
    private boolean afk;
    private Long afkSince;
    private String afkResson;

    //W przyszłości coś ekonomi :P
    private Long money;

    public User(Player player){
        //this.saveUser();
        //this.loadFile();
    }

    //TODO new parse system and load system
//    private void loadFile(){
//
//        this.file = new File("./plugins/clCore/users/", this.uuid.toString() + ".json");
//
//        if(!file.exists()){
//            if(!file.getParentFile().exists()) file.getParentFile().mkdir();
//
//            consoleLog.info("Created user " + displayname + " file!");
//
//            saveUser();
//            saveFile();
//
//        } else {
//            JsonParser jsonParser = new JsonParser();
//
//            try{
//
//                Object parsed = jsonParser.parse(new FileReader(file));
//
//                jsonObject = (JsonObject)parsed;
//
//                JsonObject last = (JsonObject) jsonObject.get("last_stay");
//
//                if(last != null) {
//                    Location location_last = new Location(Bukkit.getWorld(last.get("world").getAsString()), last.get("x").getAsInt(), last.get("y").getAsInt(), last.get("z").getAsInt());
//
//                    location_last.setYaw(last.get("yaw").getAsFloat());
//
//                    last_stay = location_last;
//                }
//
//                this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
//                this.displayname = jsonObject.get("displayname").getAsString();
//                this.last_login = jsonObject.get("last_login").getAsLong();
//                this.god = jsonObject.get("god").getAsBoolean();
//                this.home = new HashMap<String, Location>();
//                try {
//                    JsonObject home = jsonObject.get("home").getAsJsonObject();
//
//                    Gson gson = new Gson();
//
//                    HashMap<String, Home> objects = new HashMap<String, Home>();
//
//                    for(Map.Entry<String, JsonElement> entry : home.entrySet()){
//
//                        Home home_obj = gson.fromJson(entry.getValue(), Home.class);
//                        objects.put(entry.getKey(), home_obj);
//
//                    }
//
//                    for(String s : objects.keySet()){
//
//                        Home obj = objects.get(s);
//
//                        Location location = new Location(Bukkit.getWorld(obj.getWorld()), obj.getX(), obj.getY(), obj.getZ());
//                        location.setYaw(obj.getYaw());
//
//                        this.home.put(s, location);
//
//                    }
//
//                    home = null;
//                    gson = null;
//                    objects = null;
//
//                } catch (Exception e){
//
//                }
//
//                this.fly = jsonObject.get("fly").getAsBoolean();
//                this.gamemode = jsonObject.get("gamemode").getAsInt();
//                this.skin = jsonObject.get("skin").getAsString();
//                this.skin_senility = jsonObject.get("skin_senility").getAsLong();
//                this.signature = jsonObject.get("signature").getAsString();
//                this.skinset_manually = jsonObject.get("skinset_manually").getAsBoolean();
//                this.alias = jsonObject.get("alias").getAsString();
//                this.muted = jsonObject.get("muted").getAsBoolean();
//                this.muted_to = jsonObject.get("muted_to").getAsLong();
//                this.kit = null; //TODO kit system
//                this.vanish = jsonObject.get("vanish").getAsBoolean();
//                this.socialspy = jsonObject.get("socialspy").getAsBoolean();
//                this.afk = jsonObject.get("afk").getAsBoolean();
//                this.tptoogle = jsonObject.get("tptoogle").getAsBoolean();
//
//                consoleLog.info("Loaded player " + jsonObject.get("displayname") + " file!");
//
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
//    public void saveUser(){
//
//        if(jsonObject != null) jsonObject = null;
//
//        jsonObject = new JsonObject();
//
//        jsonObject.addProperty("uuid", uuid.toString());
//        jsonObject.addProperty("displayname", displayname);
//        jsonObject.addProperty("last_login", last_login);
//        jsonObject.addProperty("god", god);
//
//        if(home != null && (home.size() >= 1)){
//
//            JsonObject jsonObject_home = new JsonObject();
//
//            for(String s : home.keySet()){
//
//                JsonObject object = new JsonObject();
//
//                Location location = home.get(s);
//
//                String world = location.getWorld().getName();
//
//                double x = location.getX();
//                double y = location.getY();
//                double z = location.getZ();
//                double yaw = location.getYaw();
//
//                object.addProperty("world", world);
//                object.addProperty("x", x);
//                object.addProperty("y", y);
//                object.addProperty("z", z);
//                object.addProperty("yaw", yaw);
//
//                jsonObject_home.add(s, object);
//
//            }
//
//            jsonObject.add("home", jsonObject_home);
//
//        } else {
//            jsonObject.addProperty("home", "");
//        }
//
//        jsonObject.addProperty("fly", fly);
//        jsonObject.addProperty("gamemode", gamemode);
//        jsonObject.addProperty("skin", skin);
//        jsonObject.addProperty("skin_senility", skin_senility);
//        jsonObject.addProperty("skinset_manually", skinset_manually);
//        jsonObject.addProperty("signature", signature);
//        jsonObject.addProperty("alias", alias);
//
//        if(last_stay != null){
//
//            String world = last_stay.getWorld().getName();
//
//            double x = last_stay.getX();
//            double y = last_stay.getY();
//            double z = last_stay.getZ();
//            double yaw = last_stay.getYaw();
//
//            JsonObject jsonObject_loc = new JsonObject();
//            jsonObject_loc.addProperty("world", world);
//            jsonObject_loc.addProperty("x", x);
//            jsonObject_loc.addProperty("y", y);
//            jsonObject_loc.addProperty("z", z);
//            jsonObject_loc.addProperty("yaw", yaw);
//
//            jsonObject.add("last_stay", jsonObject_loc);
//
//        } else {
//            jsonObject.addProperty("last_stay", "");
//        }
//
//        jsonObject.addProperty("muted", muted);
//        jsonObject.addProperty("muted_to", muted_to);
//        jsonObject.addProperty("ipAddres", ipAddres);
//
//        if(kit != null && (kit.size() >= 1)){
//
//            JsonObject jsonObject_kit = new JsonObject();
//
//            for(String s : kit.keySet()){
//
//                JsonObject object = new JsonObject();
//
//                long l = kit.get(s);
//
//                jsonObject_kit.addProperty(s, l);
//
//            }
//
//            jsonObject.add("kit", jsonObject_kit);
//
//        } else {
//            jsonObject.addProperty("kit", "");
//        }
//
//        jsonObject.addProperty("vanish", vanish);
//        jsonObject.addProperty("socialspy", socialspy);
//        jsonObject.addProperty("afk", afk);
//        jsonObject.addProperty("tptoogle", tptoogle);
//
//    }
//
//    public void saveFile(){
//
//        try {
//
//            FileWriter fileWriter = new FileWriter(file);
//
//            try{
//                fileWriter.write(jsonObject.toString());
//            } catch (Exception e){
//                e.printStackTrace();
//            } finally {
//                fileWriter.flush();
//                fileWriter.close();
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}