package com.paulek.core.listeners.player;

import com.paulek.core.Core;
import com.paulek.core.data.RandomtpStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    private static String message = Lang.INFO_RANDOMTP_TELEPORTED;

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent e){

        if(Config.ENABLE_RANDOMTELEPORT) {

            Action action = e.getAction();
            Block clickedb = e.getClickedBlock();


            if ((action == Action.RIGHT_CLICK_BLOCK) && ((clickedb.getType() == Material.OAK_BUTTON) || (clickedb.getType() == Material.STONE_BUTTON))) {
                Location loc = clickedb.getLocation();
                Player player = e.getPlayer();
                if (RandomtpStorage.getList().contains(loc)) {
                    Bukkit.getScheduler().runTask(Core.getPlugin(), new Runnable() {
                        @Override
                        public void run() {


                            Location totp = Util.randomTeleport(loc.getWorld());

                            loc.getWorld().loadChunk(totp.getBlockX(), totp.getBlockZ());

                            player.teleport(totp);

                            Location locafter = player.getLocation();
                            message = Util.fixColor(message).replace("{x}", String.valueOf(locafter.getBlockX()))
                                    .replace("{y}", String.valueOf(locafter.getBlockY()))
                                    .replace("{z}", String.valueOf(locafter.getBlockZ()));
                            Util.sendActionbar(player, message);
                        }
                    });
                }
            }

        }
    }
}
