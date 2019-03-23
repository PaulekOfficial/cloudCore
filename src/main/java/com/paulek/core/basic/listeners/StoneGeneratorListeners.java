package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StoneGeneratorListeners implements Listener {

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) {

        if (Config.STONEGENERATOR_ENABLE) {

            Block block = event.getBlock();
            Location loc = new Location(block.getWorld(), block.getX(), block.getY() - 1.0D, block.getZ());
            Block sb = loc.getBlock();
            if ((block.getType() == Material.STONE) && (sb.getType() == Material.END_STONE)) {
                sb.getWorld().spawnParticle(Particle.REDSTONE, sb.getLocation(), 10, 5, 5 , 5, new Particle.DustOptions(Color.GRAY, 10));
                event.getPlayer().sendMessage(Util.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }
            if ((block.getType() == Material.OBSIDIAN) && (sb.getType() == Material.END_STONE)) {
                sb.getWorld().spawnParticle(Particle.REDSTONE, sb.getLocation(), 10, 5, 5 , 5, new Particle.DustOptions(Color.GRAY, 10));
                event.getPlayer().sendMessage(Util.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }

        }
    }

    @EventHandler
    public void onBroke(org.bukkit.event.block.BlockBreakEvent event){

        if(Config.STONEGENERATOR_ENABLE) {

            final Block block1 = event.getBlock();
            Location loc1 = block1.getLocation();
            Location stone1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() - 1.0, loc1.getZ());
            final Block sb1 = stone1.getBlock();

            final Block block2 = event.getBlock();
            Location loc2 = block1.getLocation();
            Location stone2 = new Location(loc2.getWorld(), loc2.getX(), loc2.getY() - 1.0, loc2.getZ());
            final Block sb2 = stone1.getBlock();

            if ((block1.getType() == Material.STONE) && (sb1.getType() == Material.END_STONE)) {

                Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb1.getType() == Material.END_STONE) {
                            block1.setType(Material.STONE);
                        }
                    }
                }, Config.STONEGENERATOR_REGENERATETIME * 20);

            }

            if ((block2.getType() == Material.OBSIDIAN) && (sb2.getType() == Material.END_STONE)) {

                Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb2.getType() == Material.END_STONE) {
                            block2.setType(Material.OBSIDIAN);
                        }
                    }
                }, Config.STONEGENERATOR_REGENERATETIME * 20);

            }
        }

    }

}
