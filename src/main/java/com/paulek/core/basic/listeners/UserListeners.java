package com.paulek.core.basic.listeners;

import com.paulek.core.basic.data.Users;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class UserListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Users.loadUser(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        if(Users.getUser(event.getPlayer().getUniqueId()) != null){

            Users.getUser(event.getPlayer().getUniqueId()).setLogoutlocation(event.getPlayer().getLocation());
            Users.getUser(event.getPlayer().getUniqueId()).setLastActivity(System.currentTimeMillis());

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){

        if(Users.getUser(event.getPlayer().getUniqueId()) != null){
            Users.getUser(event.getPlayer().getUniqueId()).setLastlocation(event.getTo());
        }

    }

}
