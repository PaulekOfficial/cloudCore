package com.paulek.core.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paulek.core.Core;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String lastAccountName;
    private Location logoutlocation;
    private Location lastlocation;
    private InetAddress ipAddres;
    private Map<String, Location> homes = new HashMap<>();
    private long lastActivity;
    private boolean socialSpy;
    private boolean vanish;
    private boolean tpToogle;
    private boolean tpsMonitor;

    private Core core;

    @JsonIgnore
    private boolean uptodate = true;

    public User(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @JsonIgnore
    public User(UUID uuid, Core core) {
        this.uuid = uuid;
        this.core = Objects.requireNonNull(core, "Core");

        if (Bukkit.getPlayer(uuid) != null) {
            final Player onlinePlayer = Bukkit.getPlayer(uuid);
            lastAccountName = onlinePlayer.getDisplayName();
            lastlocation = onlinePlayer.getLocation();
            ipAddres = onlinePlayer.getAddress().getAddress();
            lastActivity = System.currentTimeMillis();

        } else if (Bukkit.getOfflinePlayer(uuid) != null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            lastAccountName = offlinePlayer.getName();
            lastActivity = System.currentTimeMillis();
        }

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Location getLogoutlocation() {
        return logoutlocation;
    }

    public void setLogoutlocation(Location logoutlocation) {
        this.logoutlocation = logoutlocation;
    }

    public Location getLastlocation() {
        return lastlocation;
    }

    public void setLastlocation(Location lastlocation) {
        this.lastlocation = lastlocation;
    }

    public InetAddress getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(InetAddress ipAddres) {
        this.ipAddres = ipAddres;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getLastAccountName() {
        return lastAccountName;
    }

    public void setLastAccountName(String lastAccountName) {
        this.lastAccountName = lastAccountName;
    }

    public Location getHome(String homeName) {

        if (homes.containsKey(homeName)) {
            return homes.get(homeName);
        }

        return null;
    }

    public void addHome(String homeName, Location location) {
        homes.put(homeName, location);
    }

    public void removeHome(String homeName) {
        homes.remove(homeName);
    }

    @JsonIgnore
    public boolean isUptodate() {
        return uptodate;
    }

    @JsonIgnore
    public void setUptodate(boolean uptodate) {
        this.uptodate = uptodate;
    }

    public Map<String, Location> getHomes() {
        return homes;
    }

    public void setHomes(Map<String, Location> homes) {
        this.homes = homes;
    }
}
