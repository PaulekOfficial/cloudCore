package com.paulek.core.basic;

import com.paulek.core.Core;
import com.paulek.core.basic.data.UserStorage;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String lastAccountName;
    private Skin skin;
    private Location logoutlocation;
    private Location lastlocation;
    private InetAddress ipAddres;
    //timestamps
    private boolean afk;
    private Long afkSince;
    private String afkResson;
    private HashMap<String, Location> homes = new HashMap<>();
    private long lastActivity;
    private UserSettings settings;

    private boolean uptodate = true;

    //W przyszłości coś ekonomi :P
    private Long money;

    public User(){}

    public User(UUID uuid){
        this.uuid = uuid;

        if(Bukkit.getPlayer(uuid) != null){
            final Player onlinePlayer = Bukkit.getPlayer(uuid);
            lastAccountName = onlinePlayer.getDisplayName();
            lastlocation = onlinePlayer.getLocation();
            ipAddres = onlinePlayer.getAddress().getAddress();
            lastActivity = System.currentTimeMillis();
            settings = new UserSettings();

            if(Config.ENABLE_SKINS && (!Bukkit.getServer().getOnlineMode())) {
                Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        Skin skin = Util.getPremiumSkin(lastAccountName);
                        if (skin != null) {
                            setSkin(skin);
                            skin.applySkinForPlayers(onlinePlayer);
                            skin.updateSkinForPlayer(onlinePlayer);

                            try {
                                UserStorage.saveUserData(UserStorage.getUser(uuid));
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }

                        }
                    }
                });
            }

        } else if(Bukkit.getOfflinePlayer(uuid) != null){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            lastAccountName = offlinePlayer.getName();
            lastActivity = System.currentTimeMillis();
            settings = new UserSettings();
        }

    }

    public void setSkin(Skin skin) {
        this.skin = skin;
        uptodate = false;
    }

    public void setLogoutlocation(Location logoutlocation) {
        this.logoutlocation = logoutlocation;
        uptodate = false;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
        uptodate = false;
    }

    public void setAfkSince(Long afkSince) {
        this.afkSince = afkSince;
        uptodate = false;
    }

    public void setAfkResson(String afkResson) {
        this.afkResson = afkResson;
        uptodate = false;
    }

    public void setHomes(HashMap<String, Location> homes) {
        this.homes = homes;
        uptodate = false;
    }

    public void setMoney(Long money) {
        this.money = money;
        uptodate = false;
    }

    public void setLastlocation(Location lastlocation) {
        this.lastlocation = lastlocation;
        uptodate = false;
    }

    public void setIpAddres(InetAddress ipAddres) {
        this.ipAddres = ipAddres;
        uptodate = false;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
        uptodate = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Skin getSkin() {
        return skin;
    }

    public Location getLogoutlocation() {
        return logoutlocation;
    }

    public Location getLastlocation() {
        return lastlocation;
    }

    public InetAddress getIpAddres() {
        return ipAddres;
    }

    public boolean isAfk() {
        return afk;
    }

    public Long getAfkSince() {
        return afkSince;
    }

    public String getAfkResson() {
        return afkResson;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public Long getMoney() {
        return money;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
        uptodate = false;
    }

    public String getLastAccountName() {
        return lastAccountName;
    }

    public void setLastAccountName(String lastAccountName) {
        this.lastAccountName = lastAccountName;
    }

    public Location getHome(String homeName){

        if(homes.containsKey(homeName)){
            return homes.get(homeName);
        }

        return null;
    }

    public void addHome(String homeName, Location location){
        homes.put(homeName, location);
    }

    public void removeHome(String homeName){
        if(homes.containsKey(homeName)){
            homes.remove(homeName);
        }
        uptodate = false;
    }

    public boolean isUptodate() {
        return uptodate;
    }


    public void setUptodate(boolean uptodate) {
        this.uptodate = uptodate;
    }

    public HashMap<String, Location> getHomes() {
        return homes;
    }

    public class UserSettings {

        private boolean socialspy;
        private boolean vanish;
        private boolean tptoogle;
        private boolean tps;

        public boolean isSocialspy() {
            return socialspy;
        }

        public void setSocialspy(boolean socialspy) {
            this.socialspy = socialspy;
        }

        public boolean isVanish() {
            return vanish;
        }

        public void setVanish(boolean vanish) {
            this.vanish = vanish;
        }

        public boolean isTptoogle() {
            return tptoogle;
        }

        public void setTptoogle(boolean tptoogle) {
            this.tptoogle = tptoogle;
        }

        public boolean isTps() {
            return tps;
        }

        public void setTps(boolean tps) {
            this.tps = tps;
        }
    }
}
