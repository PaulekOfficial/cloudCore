package com.paulek.core.listeners.player;

import com.paulek.core.data.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void onRespawn(org.bukkit.event.player.PlayerRespawnEvent event){

        Location location = new Location(Bukkit.getWorld(Config.SETTINGS_SPAWN_WORLD), Config.SETTINGS_SPAWN_BLOCKX, Config.SETTINGS_SPAWN_BLOCKY, Config.SETTINGS_SPAWN_BLOCKZ);
        location.setYaw((float)Config.SETTINGS_SPAWN_YAW);

        event.setRespawnLocation(location);

    }
}
