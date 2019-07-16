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

import java.util.Random;

public class LocationUtil {

    public LocationUtil(Location location, Player player) {

        if (Config.TP_SAFELOCATION) {

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
}
