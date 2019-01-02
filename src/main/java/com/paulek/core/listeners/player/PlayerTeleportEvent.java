package com.paulek.core.listeners.player;

import com.paulek.core.Core;
import com.paulek.core.data.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerTeleportEvent implements Listener {

    @EventHandler
    public void onTeleport(org.bukkit.event.player.PlayerTeleportEvent event){

        UUID uuid = event.getPlayer().getUniqueId();
        Location location = event.getPlayer().getLocation();

        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(UserStorage.getUser(uuid) != null) {
                    //UserStorage.getUser(uuid).setLastStay(location);
                }
            }
        }, 5*20);

    }
}
