package com.paulek.core.utils;

import com.paulek.core.data.configs.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtil {

    public TeleportUtil(Location location, Player player) {

        if(Config.SETTINGS_SAFELOCATION){

            Location loc = new SafeLocationUtil(location).getLocation();

            player.teleport(loc);

            return;
        }

        player.teleport(location);

    }
}
