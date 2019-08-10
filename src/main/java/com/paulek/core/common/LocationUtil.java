package com.paulek.core.common;

import com.paulek.core.Core;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class LocationUtil {

    private static List<Material> illicitBlocks = new ArrayList<>();

    static {
        illicitBlocks.add(Material.LAVA);
        illicitBlocks.add(Material.LEGACY_STATIONARY_LAVA);
        illicitBlocks.add(Material.LEGACY_LAVA);
        illicitBlocks.add(Material.FIRE);
        illicitBlocks.add(Material.LEGACY_BED_BLOCK);
    }

    public static void disallowLiquids(){
        illicitBlocks.add(Material.WATER);
        illicitBlocks.add(Material.LEGACY_WATER);
    }

    public static Location randomLocation(World world, int maxX, int maxZ){
        Random random = new Random();

        int x = maxX * -1 + random.nextInt(maxX - (maxX * -1) + 1);
        int z = maxZ * -1 + random.nextInt(maxZ - (maxZ * -1) + 1);

        return getSafeLocation(new Location(world, x, world.getHighestBlockYAt(x, z), z));
    }

    public static boolean isUserTpToogle(Player player, Core core) {
        return core.getUsersStorage().getUser(player.getUniqueId()).isTpToogle();
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

    public static boolean isBlockDamaging(World world, double x, double y, double z){
        Block upBlock = world.getBlockAt((int)Math.round(x), (int)Math.round(y) + 1, (int)Math.round(z));
        Block middleBlock = world.getBlockAt((int)Math.round(x), (int)Math.round(y), (int)Math.round(z));
        Block downBlock = world.getBlockAt((int)Math.round(x), (int)Math.round(y) - 1, (int)Math.round(z));

        if(illicitBlocks.contains(upBlock.getType()) || illicitBlocks.contains(upBlock.getType()) || illicitBlocks.contains(upBlock.getType())){
            return true;
        }

        return upBlock.getType() != Material.AIR;

    }

    public static boolean isBlockAproveAir(World world, double x, double y, double z){

        return y > world.getMaxHeight();

    }

    public static boolean isBlockUnsafe(World world, double x, double y, double z){
        if(isBlockDamaging(world, x, y, z)){
            return true;
        }
        return isBlockAproveAir(world, x, y, z);
    }

    public static Location getSafeLocation(Location location){

        World world = location.getWorld();
        int x = (int) Math.round(location.getX());
        int y = (int) Math.round(location.getY());
        int z = (int) Math.round(location.getZ());

        //Sprowadzenie gracza ponizej maksymalnej wysokosci swiata
        while (isBlockAproveAir(world, x, y, z)){
            y -= 1;
            if(y < 0){
                y = (int) Math.round(location.getY());
                break;
            }
        }

        //Przemieszczanie lokalizacje o kratke na ukos
        if(isBlockUnsafe(world, x, y, z)){
            x = Math.round(location.getX()) == x ? x - 1 : x + 1;
            z = Math.round(location.getZ()) == z ? z - 1 : z + 1;
        }

        //Pierwsze pruby uzyskania bezpiecznej lokalizacji
        int i = 0;
        while (isBlockUnsafe(world, x, y, z)){

            i++;

            //Jezeli y jest wiekszy niz maksymalna wysokosc przemieszczamy sie o kratke w bok
            if(y >= world.getMaxHeight()){
                x += 1;
                break;
            }

            x += VECTOR_BLOCKS[i].x;
            y += VECTOR_BLOCKS[i].y;
            z += VECTOR_BLOCKS[i].z;

        }

        //Chuj wie co to robi xd, tak maja w essentials a moja wersja nie dziala
        while(isBlockUnsafe(world, x, y, z)){

            y += 1;

            if(y <= 1){
                x += 1;

                y = world.getHighestBlockYAt(x, z);

                if (x - 48 > location.getBlockX()) {
                    return null;
                }

            }

        }

        return new Location(world, x, y, z, location.getYaw(), location.getPitch());
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

    public static void safeTeleport(Config config, Location location, Player player){
        //if user godmode
        if(config.teleportSafety){
            if(isBlockUnsafe(location.getWorld(), location.getX(), location.getY(), location.getBlockZ())){
                location = getSafeLocation(location);
                player.teleport(location);
                return;
            }
        }
        player.teleport(location);
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

    public static XVector[] VECTOR_BLOCKS;

    static {
        List<XVector> blocks = new ArrayList<>();
        int radius = 5;
        for(int x = -radius; x <= radius; x++){
            for(int y = -radius; y <= radius; y++){
                for (int z = -radius; z <=radius; z++){
                    blocks.add(new XVector(x, y, z));
                }
            }
        }
        blocks.sort(Comparator.comparingDouble(vector -> (Math.pow(vector.x, 2) + Math.pow(vector.y, 2) + Math.pow(vector.z, 2))));
        VECTOR_BLOCKS = blocks.toArray(new XVector[0]);
    }

    public static class XVector{
        public double x;
        public double y;
        public double z;

        public XVector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
