package com.paulek.core.listeners.player;

import com.paulek.core.data.configs.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class PlayerShearEntityEvent implements Listener {

    @EventHandler
    public void onShear(org.bukkit.event.player.PlayerShearEntityEvent event){

        if(Config.SETTINGS_EASTEREGG_EXPLODESHEEP) {

            if (event.getEntity().getType().equals(EntityType.SHEEP)) {

                Random r = new Random();
                int random = r.nextInt(100);

                if (random >= 88) {
                    event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), 25);
                }

            }

        }

    }
}
