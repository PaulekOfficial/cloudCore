package com.paulek.core.basic.listeners;

import com.google.common.base.Charsets;
import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.event.PlayerRandomTeleportEvent;
import com.paulek.core.common.ActionBarUtil;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.XMaterial;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerJoinEvent;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.UUID;

public class RandomTeleportListener implements Listener {

    private Core core;

    public RandomTeleportListener(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent e) {
        Action action = e.getAction();
        Block clickedb = e.getClickedBlock();


        if ((action == Action.RIGHT_CLICK_BLOCK) && ((clickedb.getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (clickedb.getType() == Material.STONE_BUTTON))) {
            Location loc = clickedb.getLocation();

            if (core.getRandomTeleportButtonsStorage().get(UUID.nameUUIDFromBytes(("rtp" + new Vector3D(loc).toString()).getBytes(Charsets.UTF_8))) != null) {
                PlayerRandomTeleportEvent playerRandomTeleportEvent = new PlayerRandomTeleportEvent(e.getPlayer(), loc.getWorld(), core.getConfiguration().rtpMaxX, core.getConfiguration().rtpMaxZ, core.getConfiguration().rtpCenterX, core.getConfiguration().rtpCenterZ);
                Bukkit.getPluginManager().callEvent(playerRandomTeleportEvent);
            }

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(core.getConfiguration().rtpOnFirstJoin) {
            if (!event.getPlayer().hasPlayedBefore()) {
                PlayerRandomTeleportEvent playerRandomTeleportEvent = new PlayerRandomTeleportEvent(event.getPlayer(), event.getPlayer().getLocation().getWorld(), core.getConfiguration().rtpMaxX, core.getConfiguration().rtpMaxZ, core.getConfiguration().rtpCenterX, core.getConfiguration().rtpCenterZ);
                Bukkit.getPluginManager().callEvent(playerRandomTeleportEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerRandomTp(PlayerRandomTeleportEvent event){
            Bukkit.getScheduler().runTask(core, new Runnable() {
                        @Override
                        public void run() {
                            Location totp = LocationUtil.randomLocation(event.getWorld(), event.getMaxLocX() + event.getCenterLocX(), event.getMaxLocZ() + event.getCenterLocZ());

                            while(true){
                                if(core.getConfiguration().rtpBiomsBlackList.contains(totp.getWorld().getBiome(totp.getBlockX(), totp.getBlockZ()).name())){
                                    totp = LocationUtil.randomLocation(event.getWorld(), event.getMaxLocX() + event.getCenterLocX(), event.getMaxLocZ() + event.getCenterLocZ());
                                } else {
                                    break;
                                }
                            }

                            event.getPlayer().teleport(totp);

                            Location locafter = event.getPlayer().getLocation();
                            String message = ColorUtil.fixColor(Lang.INFO_RANDOMTP_TELEPORTED).replace("{x}", String.valueOf(locafter.getBlockX())).replace("{y}", String.valueOf(locafter.getBlockY())).replace("{z}", String.valueOf(locafter.getBlockZ()));
                            ActionBarUtil.sendActionbar(event.getPlayer(), message);
                        }
            });
    }

}
