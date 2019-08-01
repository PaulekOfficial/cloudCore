package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodModeListener implements Listener {

    private Core core;

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            User user = core.getUsersStorage().getUser(event.getEntity().getUniqueId());

            if(user.isGodMode()){
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event){
        if(event.getEntity() instanceof Player){
            User user = core.getUsersStorage().getUser(event.getEntity().getUniqueId());

            if(user.isGodMode()){
                event.setCancelled(true);
            }

        }
    }

}
