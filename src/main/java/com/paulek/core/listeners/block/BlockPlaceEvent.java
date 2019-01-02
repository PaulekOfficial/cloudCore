package com.paulek.core.listeners.block;

import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.utils.Util;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_13_R2.ParticleType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPlaceEvent implements Listener {


    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) {

        if (Config.SETTINGS_COMBAT_DISABLEPLEACING) {

            if (CombatStorage.isMarked(event.getPlayer().getUniqueId())) {
                boolean allow = false;

                for (String s : Config.SETTINGS_COMBAT_IGNORED_PLACE) {
                    Material m = Material.matchMaterial(s);

                    if (m != null) {
                        if (event.getBlock().getType().equals(m)) {
                            allow = true;
                        }
                    }

                }

                if (!allow) {
                    event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_PLACE));
                    event.setCancelled(true);
                }
            }

        }

        if (Config.ENABLE_STONEGENERATOR) {

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

}
