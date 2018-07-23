package com.paulek.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SafeLocationUtil {

    private Location location;

    public SafeLocationUtil(Location location) {

        float y_one = Bukkit.getWorld(location.getWorld().getName()).getHighestBlockYAt(location);

        Location to_fix = new Location(location.getWorld(), location.getX(), y_one, location.getZ(), location.getYaw(), location.getPitch());
        to_fix.setDirection(location.getDirection());

        float y_final = Bukkit.getWorld(to_fix.getWorld().getName()).getHighestBlockYAt(to_fix);

        Location fixed = new Location(to_fix.getWorld(), to_fix.getX(), y_final, to_fix.getZ(), to_fix.getYaw(), to_fix.getPitch());
        to_fix.setDirection(location.getDirection());



        this.location = fixed;
    }

    public Location getLocation() {
        return location;
    }
}
