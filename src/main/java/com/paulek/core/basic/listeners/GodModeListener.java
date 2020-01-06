package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.Objects;

public class GodModeListener implements Listener {

    private Core core;

    public GodModeListener(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        isGodMode(event.getEntity());
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event){
        isGodMode(event.getEntity());
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
        isGodMode(event.getEntity());
    }

    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent event){
        for(LivingEntity livingEntity : event.getAffectedEntities()){
            if(isGodMode(livingEntity)){
                event.setIntensity(livingEntity, 0D);
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent event){
        if(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED){
            event.setCancelled(isGodMode(event.getEntity()));
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        event.setCancelled(isGodMode(event.getEntity()));
    }

    @EventHandler
    public void onEntityCombustEvent(EntityCombustEvent event){
        event.setCancelled(isGodMode(event.getEntity()));
    }

    @EventHandler
    public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event){
        event.setCancelled(isGodMode(event.getEntity()));
    }

    private boolean isGodMode(Entity entity){

        if(core.getConfiguration().disabledWorldsForGodmode.contains(entity.getWorld().getName())){
            return false;
        }

        if(entity instanceof Player && core.getUsersStorage().get(entity.getUniqueId()).isGodMode()){
            Player player = (Player) entity;
            player.setFireTicks(0);
            player.setRemainingAir(player.getRemainingAir());
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(10);
            return true;
        }
        return false;
    }

}
