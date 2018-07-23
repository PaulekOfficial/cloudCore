package com.paulek.core.listeners.player;

import com.paulek.core.data.UserStorage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerTeleportEvent implements Listener {

    @EventHandler
    public void onTeleport(org.bukkit.event.player.PlayerTeleportEvent event){

        UUID uuid = event.getPlayer().getUniqueId();
        Location location = event.getPlayer().getLocation();

        UserStorage.getUsers().get(uuid).setLast_stay(location);

    }
}
