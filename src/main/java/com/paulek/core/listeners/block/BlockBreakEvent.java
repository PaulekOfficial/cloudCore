package com.paulek.core.listeners.block;

import com.paulek.core.Core;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    int regenerate = Config.SETTINGS_STONEGENERATOR_REGENERATE;

    @EventHandler
    public void onBroke(org.bukkit.event.block.BlockBreakEvent event){

        if(Config.SETTINGS_COMBAT_DISABLEBREAKING){

            if(CombatStorage.isMarked(event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_BREAKDISABLED));

                event.setCancelled(true);
            }
        }

        if(Config.ENABLE_STONEGENERATOR) {

            final Block block1 = event.getBlock();
            Location loc1 = block1.getLocation();
            Location stone1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() - 1.0, loc1.getZ());
            final Block sb1 = stone1.getBlock();

            final Block block2 = event.getBlock();
            Location loc2 = block1.getLocation();
            Location stone2 = new Location(loc2.getWorld(), loc2.getX(), loc2.getY() - 1.0, loc2.getZ());
            final Block sb2 = stone1.getBlock();

            if ((block1.getType() == Material.STONE) && (sb1.getType() == Material.ENDER_STONE)) {

                Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb1.getType() == Material.ENDER_STONE) {
                            block1.setType(Material.STONE);
                        }
                    }
                }, regenerate * 20);

            }

            if ((block2.getType() == Material.OBSIDIAN) && (sb2.getType() == Material.ENDER_STONE)) {

                Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb2.getType() == Material.ENDER_STONE) {
                            block2.setType(Material.OBSIDIAN);
                        }
                    }
                }, regenerate * 20);

            }
        }

    }
}
