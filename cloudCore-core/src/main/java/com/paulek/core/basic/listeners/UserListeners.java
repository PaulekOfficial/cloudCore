package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import common.ColorUtil;
import common.io.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserListeners implements Listener {

    private Core core;

    public UserListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        User user = core.getUsersStorage().getUser(event.getEntity().getUniqueId());

        if (user != null) {

            user.setLastlocation(event.getEntity().getLocation());
            user.setDirty(true);

        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(event.getPlayer().getDisplayName().toCharArray().length >= core.getConfiguration().maxNickLenght){
            event.getPlayer().kickPlayer(ColorUtil.fixColor(Lang.ERROR_JOIN_MAXNICKLETTER));
        }

        String joinMessage = core.getConfiguration().customJoinMessage;
        if(!joinMessage.equalsIgnoreCase("none")){
            event.setJoinMessage(joinMessage.replace("{PLAYER}", event.getPlayer().getDisplayName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String joinMessage = core.getConfiguration().customQuitMessage;
        if(!joinMessage.equalsIgnoreCase("none")){
            event.setQuitMessage(joinMessage.replace("{PLAYER}", event.getPlayer().getDisplayName()));
        }

        User user = core.getUsersStorage().getUser(event.getPlayer().getUniqueId());

        if (user != null) {

            user.setLogoutlocation(event.getPlayer().getLocation());
            user.setLastActivity(LocalDateTime.now());

            if(core.getConfiguration().removeGodmodeOnDisconect && user.isGodMode()){
                user.setGodMode(false);
            }

            user.setDirty(true);

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        User user = core.getUsersStorage().getUser(event.getPlayer().getUniqueId());

        if (user != null) {
            user.setLastlocation(event.getFrom());
            user.setDirty(true);
        }

    }

}
