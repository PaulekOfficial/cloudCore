package com.paulek.core.basic.listeners;

import com.paulek.core.common.io.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class EasterEggListeners implements Listener {

    @EventHandler
    public void onShear(org.bukkit.event.player.PlayerShearEntityEvent event) {

        if (Config.EASTEREGGS) {

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
