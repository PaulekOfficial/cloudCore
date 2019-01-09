package com.paulek.core.listeners;

import com.paulek.core.data.CombatStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.Warrior;
import com.paulek.core.utils.Util;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CombatListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        if(Config.SETTINGS_COMBAT_DISABLECHESTS) {

            if(CombatStorage.isMarked(event.getPlayer().getUniqueId())) {

                if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST)) ||
                        (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST)) ||
                        (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) ||
                        (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.ENDER_CHEST))) {

                    event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_CHESTDISABLED));

                    event.setCancelled(true);

                }
            }
        }

    }

    @EventHandler
    public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {

        if (Config.SETTINGS_COMBAT_DISABLECOMMAND) {

            if(CombatStorage.isMarked(event.getPlayer().getUniqueId())) {
                boolean found = false;

                for (String s : Config.SETTINGS_COMBAT_IGNORED_COMMANDS) {
                    if (event.getMessage().contains(s)) {
                        found = true;
                    }
                }

                if (!found) {
                    event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_COMMANDDISABLED));
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if (Config.SETTINGS_COMBAT_DISCARD) {

            if (CombatStorage.isMarked(event.getPlayer().getUniqueId())) {

                Material m = Material.matchMaterial(Config.SETTINGS_COMBAT_DISCARDBLOCK);

                if(m == null) m = Material.REDSTONE_BLOCK;

                if ((event.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.REDSTONE_BLOCK)) ||
                        (event.getPlayer().getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.REDSTONE_BLOCK))) {

                    event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().normalize().multiply(-0.68));

                    event.getPlayer().setFallDistance(0.0F);

                }
            }
        }
    }

    @EventHandler
    public void onBroke(org.bukkit.event.block.BlockBreakEvent event) {

        if (Config.SETTINGS_COMBAT_DISABLEBREAKING) {

            if (CombatStorage.isMarked(event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_BREAKDISABLED));

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) {

        if (Config.SETTINGS_COMBAT_DISABLEPLEACING) {

            if (CombatStorage.isMarked(event.getPlayer().getUniqueId())) {
                boolean allow = false;

                for (String s : Config.SETTINGS_COMBAT_IGNORED_PLACE) {
                    Material m = Material.matchMaterial(s);

                    if (m != null) {
                        if (event.getBlock().getType().equals(m)) {
                            allow = true;
                        }
                    }

                }

                if (!allow) {
                    event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_PLACE));
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onDamageEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event){

        Entity attack = event.getDamager();
        Entity damaged = event.getEntity();
        boolean test = true;

        if (!Config.SETTINGS_COMBAT_CREATIVE) {
            if (attack instanceof Player) {
                Player damager = (Player) attack;
                if (damager.getGameMode() == GameMode.CREATIVE) {
                    damager.sendMessage(Util.fixColor(Lang.ERROR_COMBAT_CREATIVE));
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
                    new CombatStorage(new Warrior(player.getUniqueId(), player.getDisplayName()));
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

                new CombatStorage(new Warrior(player.getUniqueId(), player.getDisplayName()));
                new CombatStorage(new Warrior(damager.getUniqueId(), damager.getDisplayName()));
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

                if(shotted != null) new CombatStorage(new Warrior(shotted.getUniqueId(), shotted.getDisplayName()));
                new CombatStorage(new Warrior(damage.getUniqueId(), damage.getDisplayName()));
                shotted.setLastDamage(0.5F);

                if(Config.SETTINGS_COMBAT_CHATMESSAGE){
                    shotted.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                    damager.sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
                }

            }
        }

    }

}
