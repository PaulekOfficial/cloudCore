package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.event.PlayerRandomTeleportEvent;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.XMaterial;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.Objects;

public class RandomTeleportListener implements Listener {

    private Core core;

    public RandomTeleportListener(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent e) {

        if (Config.RTP_ENABLE) {

            Action action = e.getAction();
            Block clickedb = e.getClickedBlock();


            if ((action == Action.RIGHT_CLICK_BLOCK) && ((clickedb.getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (clickedb.getType() == Material.STONE_BUTTON))) {
                Location loc = clickedb.getLocation();

                if (core.getRtpsStorage().getList().contains(loc)) {
                    PlayerRandomTeleportEvent playerRandomTeleportEvent = new PlayerRandomTeleportEvent(e.getPlayer(), loc.getWorld(), Config.RTP_MAXVALUES_X, Config.RTP_MAXVALUES_X);
                    Bukkit.getPluginManager().callEvent(playerRandomTeleportEvent);
                }

            }

        }
    }

    @EventHandler
    public void onPlayerRandomTp(PlayerRandomTeleportEvent event){
            Bukkit.getScheduler().runTask(core, new Runnable() {
                        @Override
                        public void run() {
                            Location totp = LocationUtil.randomLocation(event.getWorld(), event.getMaxLocX(), event.getMaxLocZ());

                            event.getPlayer().teleport(totp);

                            Location locafter = event.getPlayer().getLocation();
                            String message = Util.fixColor(Lang.INFO_RANDOMTP_TELEPORTED).replace("{x}", String.valueOf(locafter.getBlockX())).replace("{y}", String.valueOf(locafter.getBlockY())).replace("{z}", String.valueOf(locafter.getBlockZ()));
                            Util.sendActionbar(event.getPlayer(), message);
                        }
            });
    }

}
