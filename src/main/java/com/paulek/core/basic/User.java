package com.paulek.core.basic;

import com.paulek.core.Core;
import org.bukkit.Location;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String lastAccountName;
    private Location logoutlocation;
    private Location lastlocation;
    private InetAddress ipAddres;
    private Map<String, Location> homes;
    private LocalDateTime lastActivity;
    private boolean socialSpy;
    private boolean vanish;
    private boolean tpToogle;
    private boolean tpsMonitor;
    private boolean godMode;

    private boolean dirty = true;

    public User(UUID uuid, String lastAccountName, Location logoutlocation, Location lastlocation, InetAddress ipAddres, Map<String, Location> homes, LocalDateTime lastActivity, boolean socialSpy, boolean vanish, boolean tpToogle, boolean tpsMonitor, boolean godMode, Core core) {
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
        this.godMode = godMode;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        this.dirty = true;
    }

    public Location getLogoutlocation() {
        return logoutlocation;
    }

    public void setLogoutlocation(Location logoutlocation) {
        this.logoutlocation = logoutlocation;
        this.dirty = true;
    }

    public Location getLastlocation() {
        return lastlocation;
    }

    public void setLastlocation(Location lastlocation) {
        this.lastlocation = lastlocation;
        this.dirty = true;
    }

    public InetAddress getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(InetAddress ipAddres) {
        this.ipAddres = ipAddres;
        this.dirty = true;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
        this.dirty = true;
    }

    public String getLastAccountName() {
        return lastAccountName;
    }

    public void setLastAccountName(String lastAccountName) {
        this.lastAccountName = lastAccountName;
        this.dirty = true;
    }

    public Location getHome(String homeName) {

        if (homes.containsKey(homeName)) {
            return homes.get(homeName);
        }

        return null;
    }

    public void addHome(String homeName, Location location) {
        homes.put(homeName, location);
        this.dirty = true;
    }

    public void removeHome(String homeName) {
        homes.remove(homeName);
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
        this.dirty = true;
    }

    public Map<String, Location> getHomes() {
        return homes;
    }

    public void setHomes(Map<String, Location> homes) {
        this.dirty = true;
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
        this.dirty = true;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
        this.dirty = true;
    }

    public void setTpToogle(boolean tpToogle) {
        this.tpToogle = tpToogle;
        this.dirty = true;
    }

    public void setTpsMonitor(boolean tpsMonitor) {
        this.tpsMonitor = tpsMonitor;
        this.dirty = true;
    }
}
