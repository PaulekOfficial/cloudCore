package com.paulek.core.basic.data;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.User;
import com.paulek.core.common.*;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Users implements Runnable {

    private ObjectMapper mapper = new ObjectMapper();

    private Map<UUID, User> users = new HashMap<UUID, User>();

    public Map<UUID, User> getUsers() {
        return users;
    }

    private Core core;

    public Users(Core core){

        this.core = Objects.requireNonNull(core, "Core");

        SimpleModule locationSerializerModule = new SimpleModule("LocationSerializer", new Version(1, 0, 0 ,null, null, null));
        locationSerializerModule.addSerializer(Location.class, new LocationSerializer());

        SimpleModule locationDeserializerModule = new SimpleModule("LocationDeserializer", new Version(1, 0, 0, null, null, null));
        locationDeserializerModule.addDeserializer(Location.class, new LocationDeserializer());

        SimpleModule skinDeserializerModule = new SimpleModule("SkinDeserializer", new Version(1, 0, 0, null, null, null));
        locationDeserializerModule.addDeserializer(Skin.class, new SkinDeserializer());

        mapper.registerModule(locationSerializerModule);
        mapper.registerModule(locationDeserializerModule);
        mapper.registerModule(skinDeserializerModule);

        File directory = new File(core.getPlugin().getDataFolder(), "users");

        if(!directory.exists()){
            directory.mkdir();
        } else {
            Long startTime = System.currentTimeMillis();
            int loadedUsers = 0;

            for(File f : directory.listFiles()){

                try {
                    User user = loadUserData(f);
                    users.put(user.getUuid(), user);
                    loadedUsers++;
                } catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
            int timeTook = (int) (System.currentTimeMillis() - startTime);
            consoleLog.info("Loaded " + loadedUsers + " users. Took: " + timeTook + "ms.");
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(core.getPlugin(), this, 20*60*5, 20*60*10);

    }

    public void removeUser(UUID uuid){
        users.remove(uuid);
    }

    public void loadUser(UUID uuid){
        Long startTime = System.currentTimeMillis();
        if(!users.containsKey(uuid)){
            User user = new User(uuid);
            users.put(uuid, user);

            Bukkit.getScheduler().runTaskLaterAsynchronously(core.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    try {
                        saveUserData(user);
                    } catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }, 20*60);

        } else {
            Bukkit.getScheduler().runTaskAsynchronously(core.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    User user = users.get(uuid);
                    Player onlinePlayer = Bukkit.getPlayer(uuid);

                    user.setLastAccountName(onlinePlayer.getDisplayName());
                    user.setIpAddres(onlinePlayer.getAddress().getAddress());
                    user.setLastlocation(onlinePlayer.getLocation());
                    user.setLastActivity(System.currentTimeMillis());

                    if(Config.SKINS_ENABLE && (!Bukkit.getServer().getOnlineMode())) {
                        Skin skin = user.getSkin();

                        if (skin != null) {

                            int timeSinceTheLastSkinUpdate = (int) (((System.currentTimeMillis() / 1000L) - (skin.getLastUpdate() / 1000L)) / 60);

                            if ((432000 - timeSinceTheLastSkinUpdate) < 0) {

                                skin = Util.getPremiumSkin(onlinePlayer.getDisplayName());

                                if (skin != null) {
                                    user.setSkin(skin);
                                }

                            }
                            skin.applySkinForPlayers(onlinePlayer);
                            skin.updateSkinForPlayer(onlinePlayer);
                        }
                    }

                    users.replace(uuid, users.get(uuid), user);

                    try{
                        saveUserData(getUser(uuid));
                    } catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            });
        }
        int timeTook = (int) (System.currentTimeMillis() - startTime);
        consoleLog.info("Loaded " + uuid.toString() + " user file. Took: " + timeTook + "ms.");
    }

    @Override
    public void run(){

        for(User u : users.values()){
            if(!u.isUptodate()){
                u.setUptodate(true);
                Bukkit.getScheduler().runTaskAsynchronously(core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveUserData(u);
                        } catch (IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    private User loadUserData(File file) throws IOException {
        return mapper.readValue(file, User.class);
    }

    public void saveUserData(User user) throws IOException{
        Long startTime = System.currentTimeMillis();
        File file = new File(core.getPlugin().getDataFolder(), "users/" + user.getUuid().toString() + ".json");

        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }
        }

        mapper.writeValue(file, user);
        int timeTook = (int) (System.currentTimeMillis() - startTime);
        consoleLog.info("Saved " + user.getLastAccountName() + " user file. Took: " + timeTook + "ms.");
    }

    public User getUser(UUID uuid){
        return users.get(uuid);
    }
}
