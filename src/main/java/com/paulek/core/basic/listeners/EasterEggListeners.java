package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.Random;

public class EasterEggListeners implements Listener {

    private Core core;

    public EasterEggListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onShear(org.bukkit.event.player.PlayerShearEntityEvent event) {

        if (core.getConfiguration().easteregg) {

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
