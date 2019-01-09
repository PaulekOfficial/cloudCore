package com.paulek.core.listeners;

import com.paulek.core.data.UserStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class UserListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        UserStorage.loadUser(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        if(UserStorage.getUser(event.getPlayer().getUniqueId()) != null){

            UserStorage.getUser(event.getPlayer().getUniqueId()).setLogoutlocation(event.getPlayer().getLocation());
            UserStorage.getUser(event.getPlayer().getUniqueId()).setLastActivity(System.currentTimeMillis());

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){

        if(UserStorage.getUser(event.getPlayer().getUniqueId()) != null){
            UserStorage.getUser(event.getPlayer().getUniqueId()).setLastlocation(event.getTo());
        }

    }

}
