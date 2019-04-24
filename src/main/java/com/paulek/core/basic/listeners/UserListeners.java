package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Users;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class UserListeners implements Listener {

    private Core core;

    public UserListeners(Core core){
        this.core = Objects.requireNonNull(core, "Ccre");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        core.getUsersStorage().loadUser(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {

            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLogoutlocation(event.getPlayer().getLocation());
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastActivity(System.currentTimeMillis());

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastlocation(event.getTo());
        }

    }

}
