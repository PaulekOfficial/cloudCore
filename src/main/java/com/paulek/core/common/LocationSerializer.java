package com.paulek.core.common;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LocationSerializer {

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
