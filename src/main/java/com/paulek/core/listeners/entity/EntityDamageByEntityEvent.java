package com.paulek.core.listeners.entity;

import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.data.objects.PlayerObject;
import com.paulek.core.utils.Util;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntityEvent implements Listener {

    private boolean cleativecombat = Config.SETTINGS_COMBAT_CREATIVE;
    private String crativecombat_message = Util.fixColor(Lang.ERROR_COMBAT_CREATIVE);

    @EventHandler
    public void onDamageEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event){

        Entity attack = event.getDamager();
        Entity damaged = event.getEntity();
        boolean test = true;

        if (!cleativecombat) {
            if (attack instanceof Player) {
                Player damager = (Player) attack;
                if (damager.getGameMode() == GameMode.CREATIVE) {
                    damager.sendMessage(crativecombat_message);
                    event.setCancelled(true);
                    test = false;
                    return;
                }
            }

        }
        if (test) {
            if(Config.SETTINGS_COMBAT_MOBDAMAGE) {
                if ((damaged instanceof Player) && (attack instanceof Monster)) {
                    Player player = (Player) damaged;
                    if (CombatStorage.isMarked(player.getUniqueId())) {
                        CombatStorage.changeTimeMilisrs(player.getUniqueId(), System.currentTimeMillis());
                        return;
                    }
                    new CombatStorage(new PlayerObject(player.getUniqueId(), player.getDisplayName()));
                    if (Config.SETTINGS_COMBAT_CHATMESSAGE) {
                        player.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                    }
                }
            }

            if ((attack instanceof Player) && (damaged instanceof Player)) {
                Player player = (Player) damaged;
                Player damager = (Player) attack;
                if (CombatStorage.isMarked(player.getUniqueId()) && CombatStorage.isMarked(damager.getUniqueId())) {
                    CombatStorage.changeTimeMilisrs(player.getUniqueId(), System.currentTimeMillis());
                    CombatStorage.changeTimeMilisrs(damager.getUniqueId(), System.currentTimeMillis());
                    return;
                }

                new CombatStorage(new PlayerObject(player.getUniqueId(), player.getDisplayName()));
                new CombatStorage(new PlayerObject(damager.getUniqueId(), damager.getDisplayName()));
                if(Config.SETTINGS_COMBAT_CHATMESSAGE){
                    player.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                    damager.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                }

            }
            if (((event.getDamager() instanceof Projectile)) && ((event.getEntity() instanceof Player)) &&
                    ((((Projectile) event.getDamager()).getShooter() instanceof Player))) {
                Player damage = null;
                Player shotted = null;
                Entity damager = event.getDamager();
                if (event.getDamager() instanceof Arrow) {
                    shotted = ((Player) event.getEntity()).getPlayer();
                    damage = (Player) ((Projectile) damager).getShooter();
                }
                if (event.getDamager() instanceof ThrownPotion) {
                    shotted = ((Player) event.getEntity()).getPlayer();
                    damage = (Player) ((Projectile) damager).getShooter();
                }
                if (event.getDamager() instanceof Snowball) {
                    shotted = ((Player) event.getEntity()).getPlayer();
                    damage = (Player) ((Projectile) damager).getShooter();
                }
                if (event.getDamager() instanceof Egg) {
                    shotted = ((Player) event.getEntity()).getPlayer();
                    damage = (Player) ((Projectile) damager).getShooter();
                }

                if(shotted != null) new CombatStorage(new PlayerObject(shotted.getUniqueId(), shotted.getDisplayName()));
                new CombatStorage(new PlayerObject(damage.getUniqueId(), damage.getDisplayName()));
                shotted.setLastDamage(0.5F);

                if(Config.SETTINGS_COMBAT_CHATMESSAGE){
                    shotted.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                    damager.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                }

            }
        }

    }

}
