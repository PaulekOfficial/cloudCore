package com.paulek.core.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paulek.core.Core;
import org.bukkit.Location;

import java.net.InetAddress;
import java.time.LocalDateTime;
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
    private LocalDateTime lastActivity;
    private boolean socialSpy;
    private boolean vanish;
    private boolean tpToogle;
    private boolean tpsMonitor;

    private Core core;

    @JsonIgnore
    private boolean dirty = true;

    public User(UUID uuid, String lastAccountName, Location logoutlocation, Location lastlocation, InetAddress ipAddres, Map<String, Location> homes, LocalDateTime lastActivity, boolean socialSpy, boolean vanish, boolean tpToogle, boolean tpsMonitor, Core core) {
        this.uuid = uuid;
        this.lastAccountName = lastAccountName;
        this.logoutlocation = logoutlocation;
        this.lastlocation = lastlocation;
        this.ipAddres = ipAddres;
        this.homes = homes;
        this.lastActivity = lastActivity;
        this.socialSpy = socialSpy;
        this.vanish = vanish;
        this.tpToogle = tpToogle;
        this.tpsMonitor = tpsMonitor;
        this.core = Objects.requireNonNull(core, "Core");
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

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
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
    public boolean isDirty() {
        return dirty;
    }

    @JsonIgnore
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Map<String, Location> getHomes() {
        return homes;
    }

    public void setHomes(Map<String, Location> homes) {
        this.homes = homes;
    }

    public boolean isSocialSpy() {
        return socialSpy;
    }

    public boolean isVanish() {
        return vanish;
    }

    public boolean isTpToogle() {
        return tpToogle;
    }

    public boolean isTpsMonitor() {
        return tpsMonitor;
    }

    public void setSocialSpy(boolean socialSpy) {
        this.socialSpy = socialSpy;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public void setTpToogle(boolean tpToogle) {
        this.tpToogle = tpToogle;
    }

    public void setTpsMonitor(boolean tpsMonitor) {
        this.tpsMonitor = tpsMonitor;
    }
}
