package com.paulek.core.common;

import com.paulek.core.Core;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class LocationUtil {

    private Core core;

    public LocationUtil(Location location, Player player, Core core) {

        this.core = Objects.requireNonNull(core, "Core");

        if (core.getConfiguration().teleportSafety) {

            player.teleport(safeLocation(location));

            return;
        }

        player.teleport(location);

    }

    public static Location randomLocation(World world, int maxX, int maxZ) {
        Random RAND = new Random();
        int x = (maxX * -1) + RAND.nextInt(maxX -
                (maxX * -1) + 1);
        int z = (maxZ * -1) + RAND.nextInt(maxZ - (maxZ * -1) + 1);
        int y = world.getHighestBlockYAt(x, z);
        if ((world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.OCEAN) || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.DEEP_OCEAN)
                || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.FROZEN_OCEAN)) {
            randomLocation(world, maxX, maxZ);
        }
        Location loc = new Location(world, x, y, z);
        int yfix = world.getHighestBlockYAt(loc);
        return new Location(world, x, yfix, z);
    }

    public static boolean hasPlayerTpToogle(Player player, Core core) {
        return core.getUsersStorage().getUser(player.getUniqueId()).isTpToogle();
    }

    public Location safeLocation(Location location) {

        float y_one = Bukkit.getWorld(location.getWorld().getName()).getHighestBlockYAt(location);

        Location to_fix = new Location(location.getWorld(), location.getX(), y_one, location.getZ(), location.getYaw(), location.getPitch());
        to_fix.setDirection(location.getDirection());

        float y_final = Bukkit.getWorld(to_fix.getWorld().getName()).getHighestBlockYAt(to_fix);

        Location fixed = new Location(to_fix.getWorld(), to_fix.getX(), y_final, to_fix.getZ(), to_fix.getYaw(), to_fix.getPitch());
        to_fix.setDirection(location.getDirection());

        return fixed;
    }

    public boolean infoPlayerHasTpToogle(Player player, CommandSender sender, Core core) {
        if (hasPlayerTpToogle(player, core)) {

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPTOOGLE_TPDENY));

            return true;
        }
        return false;
    }

    public static String locationToString(Location location){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getWorld().getUID().toString());
        stringBuilder.append("#");
        stringBuilder.append(location.getX());
        stringBuilder.append("#");
        stringBuilder.append(location.getY());
        stringBuilder.append("#");
        stringBuilder.append(location.getX());
        stringBuilder.append("#");
        stringBuilder.append(location.getYaw());
        stringBuilder.append("#");
        stringBuilder.append(location.getPitch());
        return stringBuilder.toString();
    }

    public static Location locationFromString(String location){

        if (location.equalsIgnoreCase("") || location == null) return null;

        String[] splittedString = location.split("#");
        World world = Bukkit.getWorld(UUID.fromString(splittedString[0]));
        double x = Double.parseDouble(splittedString[1]);
        double y = Double.parseDouble(splittedString[2]);
        double z = Double.parseDouble(splittedString[3]);
        float yaw = Float.parseFloat(splittedString[4]);
        float pitch = Float.parseFloat(splittedString[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String locationMapToString(Map<String, Location> locationMap){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : locationMap.keySet()){
            stringBuilder.append(key);
            stringBuilder.append("#");
            stringBuilder.append(locationToString(locationMap.get(key)));
            stringBuilder.append("%");
        }
        return stringBuilder.toString();
    }

    public static Map<String, Location> locationMapFormString(String string){
        Map<String, Location> map = new HashMap<>();

        if(string.equalsIgnoreCase("") || string == null) return map;

        String[] splittedStringPhaseOne = string.split("%");

        for(String dataPack : splittedStringPhaseOne){

            String[] splittedStringPhaseTwo = dataPack.split("#");

            String name = splittedStringPhaseTwo[0];
            World world = Bukkit.getWorld(UUID.fromString(splittedStringPhaseTwo[1]));
            double x = Double.parseDouble(splittedStringPhaseTwo[2]);
            double y = Double.parseDouble(splittedStringPhaseTwo[3]);
            double z = Double.parseDouble(splittedStringPhaseTwo[4]);
            float yaw = Float.parseFloat(splittedStringPhaseTwo[5]);
            float pitch = Float.parseFloat(splittedStringPhaseTwo[6]);

            Location location = new Location(world, x, y, z, yaw, pitch);

            map.put(name, location);
        }

        return map;
    }
}
