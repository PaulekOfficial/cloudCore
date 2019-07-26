package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.User;
import com.paulek.core.common.MojangApiUtil;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class UserListeners implements Listener {

    private Core core;

    public UserListeners(Core core){
        this.core = Objects.requireNonNull(core, "Ccre");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        User user = core.getUsersStorage().getUser(event.getEntity().getUniqueId());
        user.setLastlocation(event.getEntity().getLocation());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {

            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLogoutlocation(event.getPlayer().getLocation());
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastActivity(LocalDateTime.now());

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastlocation(event.getFrom());
        }

    }

}
