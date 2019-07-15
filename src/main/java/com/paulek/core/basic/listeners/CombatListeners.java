package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.Warrior;
import com.paulek.core.basic.data.localStorage.CombatStorage;
import com.paulek.core.basic.event.PlayerCombatStartEvent;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class CombatListeners implements Listener {

    private Core core;

    public CombatListeners(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (Config.COMBAT_DISABLECHESTS) {

            if (core.getCombatStorage().isMarked(event.getPlayer().getUniqueId())) {

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

        if (Config.COMBAT_DISABLECOMMAND) {

            if (core.getCombatStorage().isMarked(event.getPlayer().getUniqueId())) {
                boolean found = false;

                for (String s : Config.COMBAT_IGNORED_COMMANDS) {
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
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (core.getCombatStorage().isMarked(player.getUniqueId()))
            core.getCombatStorage().unmark(player.getUniqueId());

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Config.COMBAT_DISCARD) {

            if (core.getCombatStorage().isMarked(event.getPlayer().getUniqueId())) {

                Material m = Material.matchMaterial(Config.COMBAT_DISCARDBLOCK);

                if (m == null) m = Material.REDSTONE_BLOCK;

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

        if (Config.COMBAT_DISABLEBREAKING) {

            if (core.getCombatStorage().isMarked(event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_BREAKDISABLED));

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) {

        if (Config.COMBAT_DISABLEPLEACING) {

            if (core.getCombatStorage().isMarked(event.getPlayer().getUniqueId())) {
                boolean allow = false;

                for (String s : Config.COMBAT_IGNORED_BLOCKS) {
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
    public void onPlayerCombatStart(PlayerCombatStartEvent event){

        CombatStorage combatStorage = core.getCombatStorage();

        if(combatStorage.isMarked(event.getAttacked().getUniqueId())){
            combatStorage.changeTimeMilisrs(event.getAttacked().getUniqueId(), System.currentTimeMillis());
        }
        combatStorage.addMarkedWarrior(new Warrior(event.getAttacked().getUniqueId(), event.getAttacked().getDisplayName()));
        if (Config.COMBAT_CHATMESSAGE) {
            event.getAttacked().sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
        }
        if(event.getAttacker() instanceof Player){
            if(combatStorage.isMarked((event.getAttacker().getUniqueId()))){
                combatStorage.changeTimeMilisrs(event.getAttacker().getUniqueId(), System.currentTimeMillis());
            }
            combatStorage.addMarkedWarrior(new Warrior(event.getAttacker().getUniqueId(), ((Player) event.getAttacker()).getDisplayName()));
            if (Config.COMBAT_CHATMESSAGE) {
                event.getAttacker().sendMessage(Util.fixColor(Lang.INFO_COMBAT_CHAT));
            }
        }

    }

    @EventHandler
    public void onDamageEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();
        Location location = attacker.getLocation();

        RegionContainer regionContainer = core.getWorldGuard().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(location.getWorld()));
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()));


        for(ProtectedRegion region : applicableRegionSet.getRegions()){
            if(region.getFlags().get(Flags.PVP).equals(false)){
                event.setCancelled(true);
                return;
            }
        }

        if (!Config.COMBAT_ONCREATIVE) {
            if (attacker instanceof Player) {
                Player damager = (Player) attacker;
                if (damager.getGameMode() == GameMode.CREATIVE) {
                    damager.sendMessage(Util.fixColor(Lang.ERROR_COMBAT_CREATIVE));
                    event.setCancelled(true);
                    return;
                }
            }

        }

        if (Config.COMBAT_MOBDAMAGE) {
            if ((attacked instanceof Player) && (attacker instanceof Monster)) {
                Player player = (Player) attacked;
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
        }

        if ((attacker instanceof Player) && (attacked instanceof Player)) {
            Player player = (Player) attacked;
            Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
        }
            if (((attacker instanceof Projectile)) && ((attacked instanceof Player)) && ((((Projectile) attacker).getShooter() instanceof Player))) {
            if (attacker instanceof Arrow) {
                Player player = (Player) attacked;
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof ThrownPotion) {
                Player player = (Player) attacked;
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof Snowball) {
                Player player = (Player) attacked;
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof Egg) {
                Player player = (Player) attacked;
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
        }
    }
}
