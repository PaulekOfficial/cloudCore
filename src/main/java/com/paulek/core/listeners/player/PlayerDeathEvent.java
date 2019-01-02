package com.paulek.core.listeners.player;

import com.paulek.core.data.UserStorage;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event){

        if(event.getEntity().getType().equals(EntityType.PLAYER)){

            UUID uuid = event.getEntity().getPlayer().getUniqueId();

            //UserStorage.getUser(uuid).setLastStay(event.getEntity().getPlayer().getLocation());

        }

    }
}
