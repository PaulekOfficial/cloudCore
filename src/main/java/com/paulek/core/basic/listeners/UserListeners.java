package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class UserListeners implements Listener {

    private Core core;

    public UserListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        User user = core.getUsersStorage().get(event.getEntity().getUniqueId());
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
        Player player = event.getPlayer();
        User user = core.getUsersStorage().get(player.getUniqueId());
        if(user == null) {
            user = new User(player.getUniqueId(), player.getDisplayName(), player.getLocation(), player.getLocation(), player.getAddress().getAddress(), new HashMap<>(), null, false, false, false, false, false, core);
            user.setDirty(true);
            core.getUsersStorage().add(player.getUniqueId(), user);
            core.getConsoleLog().info("Created new " + user.getUuid().toString() + " user known as " + player.getDisplayName());
        } else {
            core.getConsoleLog().info("Loaded " + user.getUuid().toString() + " user known as " + player.getDisplayName());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String joinMessage = core.getConfiguration().customQuitMessage;
        if(!joinMessage.equalsIgnoreCase("none")){
            event.setQuitMessage(joinMessage.replace("{PLAYER}", event.getPlayer().getDisplayName()));
        }
        User user = core.getUsersStorage().get(event.getPlayer().getUniqueId());
        if (user != null) {
            user.setLogoutlocation(event.getPlayer().getLocation());
            user.setLastActivity(LocalDateTime.now());
            if(core.getConfiguration().removeGodmodeOnDisconect && user.isGodMode()){
                user.setGodMode(false);
            }
            core.getUsersStorage().saveAndDelete(user.getUuid());
        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        User user = core.getUsersStorage().get(event.getPlayer().getUniqueId());
        if (user != null) {
            user.setLastlocation(event.getFrom());
            user.setDirty(true);
        }
    }

}
