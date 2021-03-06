package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.data.cache.Combats;
import com.paulek.core.basic.event.PlayerCombatStartEvent;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.ReflectionUtils;
import com.paulek.core.common.io.Lang;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

public class CombatListeners implements Listener {

    private Core core;

    public CombatListeners(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {
            core.getCombatsStorage().delete(event.getPlayer().getUniqueId());

            if (core.getConfiguration().combatAnnouncement) {

                String s = ColorUtil.fixColor(Lang.INFO_COMBAT_BRODCASTLOGOUT).replace("{player}", event.getPlayer().getName()).replace("{health}", (int) event.getPlayer().getHealth() + "♥");

                Bukkit.broadcastMessage(s);

            }

            if (core.getConfiguration().combatKillOnQuit) event.getPlayer().setHealth(0.0);

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (!core.getConfiguration().combatAllowChests) {

            if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {

                if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST)) ||
                        (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.CHEST)) ||
                        (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) ||
                        (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.ENDER_CHEST))) {

                    event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_COMBAT_CHESTDISABLED));

                    event.setCancelled(true);

                }
            }
        }

    }

    @EventHandler
    public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {

        if (!core.getConfiguration().combatAllowCommands) {

            if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {
                boolean found = false;

                for (String s : core.getConfiguration().combatCommandsAllowed) {
                    if (event.getMessage().contains(s)) {
                        found = true;
                    }
                }

                if (!found) {
                    event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_COMBAT_COMMANDDISABLED));
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (core.getCombatsStorage().get(player.getUniqueId()) != null){
            core.getCombatsStorage().delete(player.getUniqueId());
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (core.getConfiguration().comabtRejected) {

            if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {

                Material m = Material.matchMaterial(core.getConfiguration().comabtRejectedBlock);

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

        if (!core.getConfiguration().combatAllowBlockBreak) {

            if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_COMBAT_BREAKDISABLED));

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) {

        if (!core.getConfiguration().combatAllowBlockPlace) {

            if (core.getCombatsStorage().get(event.getPlayer().getUniqueId()) != null) {
                boolean allow = false;

                for (String s : core.getConfiguration().combatBlocksAllowed) {
                    Material m = Material.matchMaterial(s);

                    if (m != null) {
                        if (event.getBlock().getType().equals(m)) {
                            allow = true;
                        }
                    }

                }

                if (!allow) {
                    event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_COMBAT_PLACE));
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onPlayerCombatStart(PlayerCombatStartEvent event){

        Combats combatStorage = core.getCombatsStorage();

        combatStorage.add(event.getAttacked().getUniqueId(), LocalDateTime.now());
        if (core.getConfiguration().combatChat) {
            event.getAttacked().sendMessage(ColorUtil.fixColor(Lang.INFO_COMBAT_CHAT));
        }
        if(event.getAttacker() instanceof Player){
            combatStorage.add(event.getAttacker().getUniqueId(), LocalDateTime.now());
            if (core.getConfiguration().combatChat) {
                event.getAttacker().sendMessage(ColorUtil.fixColor(Lang.INFO_COMBAT_CHAT));
            }
        }

    }

    @EventHandler
    public void onDamageEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();
        Location location = attacker.getLocation();

        if (!core.getConfiguration().combatAllowCreative) {
            if (attacker instanceof Player) {
                Player damager = (Player) attacker;
                if(!canDamage(location)){
                    return;
                }
                if (damager.getGameMode() == GameMode.CREATIVE) {
                    damager.sendMessage(ColorUtil.fixColor(Lang.ERROR_COMBAT_CREATIVE));
                    event.setCancelled(true);
                    return;
                }
            }

        }

        if (core.getConfiguration().combatMob) {
            if ((attacked instanceof Player) && (attacker instanceof Monster)) {
                Player player = (Player) attacked;
                if(!canDamage(location)){
                    return;
                }
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
        }

        if ((attacker instanceof Player) && (attacked instanceof Player)) {
            Player player = (Player) attacked;
            if(!canDamage(location)){
                return;
            }
            Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
        }
            if (((attacker instanceof Projectile)) && ((attacked instanceof Player)) && ((((Projectile) attacker).getShooter() instanceof Player))) {
            if (attacker instanceof Arrow) {
                Player player = (Player) attacked;
                if(!canDamage(location)){
                    return;
                }
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof ThrownPotion) {
                Player player = (Player) attacked;
                if(!canDamage(location)){
                    return;
                }
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof Snowball) {
                Player player = (Player) attacked;
                if(!canDamage(location)){
                    return;
                }
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
            if (attacker instanceof Egg) {
                Player player = (Player) attacked;
                if(!canDamage(location)){
                    return;
                }
                Bukkit.getPluginManager().callEvent(new PlayerCombatStartEvent(player, attacker));
            }
        }
    }

    private boolean canDamage(Location location){

        //New wg
        try {
            Object worldGuard = core.getWorldGuard();

            Class regionContainerClass = Class.forName("com.sk89q.worldguard.protection.regions.RegionContainer");
            Class worldGuardPlatformClass = Class.forName("com.sk89q.worldguard.internal.platform.WorldGuardPlatform");
            Class bukkitAdapterClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter");
            Class applicableRegionSetClass = Class.forName("com.sk89q.worldguard.protection.ApplicableRegionSet");
            Class blockVector3Class = Class.forName("com.sk89q.worldedit.math.BlockVector3");
            Class regionAssociableClass = Class.forName("com.sk89q.worldguard.protection.association.RegionAssociable");
            Class stateFlagArrayClass = Class.forName("[Lcom.sk89q.worldguard.protection.flags.StateFlag;");
            Class flagsClass = Class.forName("com.sk89q.worldguard.protection.flags.Flags");
            Class regionManagerClass = Class.forName("com.sk89q.worldguard.protection.managers.RegionManager");

            Method getPlatform = ReflectionUtils.getMethod(worldGuard.getClass(), "getPlatform");
            Method getRegionContainer = ReflectionUtils.getMethod(worldGuardPlatformClass, "getRegionContainer");
            Method adapt = ReflectionUtils.getMethod(bukkitAdapterClass, "adapt", World.class);
            Method get = ReflectionUtils.getMethod(regionContainerClass, "get", Class.forName("com.sk89q.worldedit.world.World"));
            Method at = ReflectionUtils.getMethod(blockVector3Class, "at", Double.TYPE, Double.TYPE, Double.TYPE);
            Method getApplicableRegions = ReflectionUtils.getMethod(regionManagerClass, "getApplicableRegions", blockVector3Class);
            Method queryState = ReflectionUtils.getMethod(applicableRegionSetClass, "queryState", regionAssociableClass, stateFlagArrayClass);

            Object worldGuardPlatform = getPlatform.invoke(worldGuard, null);
            Object regionContainer = getRegionContainer.invoke(worldGuardPlatform, null);
            Object adaptedWorld = adapt.invoke(null, location.getWorld());
            Object regionManager = get.invoke(regionContainer, adaptedWorld);
            Object blockVector3 = at.invoke(null, location.getX(), location.getY(), location.getY());
            Object applicableRegionSet = getApplicableRegions.invoke(regionManager, blockVector3);
            Object denyState = Class.forName("com.sk89q.worldguard.protection.flags.StateFlag$State").getEnumConstants()[1];

            Field pvpFlagFiled = flagsClass.getDeclaredField("PVP");

            Object pvpFlag = pvpFlagFiled.get(null);

            Object flagsArray = Array.newInstance(Class.forName("com.sk89q.worldguard.protection.flags.StateFlag"), 1);
            Array.set(flagsArray, 0, pvpFlag);

            if (queryState.invoke(applicableRegionSet, null, flagsArray) == denyState) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Old wg
            try {
                Object worldGuard = core.getWorldGuard();
                Class regionManagerClass = Class.forName("com.sk89q.worldguard.protection.managers.RegionManager");
                Class defaultFlagClass = Class.forName("com.sk89q.worldguard.protection.flags.DefaultFlag");

                Method getRegionManager = ReflectionUtils.getMethod(worldGuard.getClass(), "getRegionManager", World.class);
                Method getApplicableRegions = ReflectionUtils.getMethod(regionManagerClass, "getApplicableRegions", Location.class);
                Method queryState = ReflectionUtils.getMethod(Class.forName("com.sk89q.worldguard.protection.ApplicableRegionSet"), "queryState", Class.forName("com.sk89q.worldguard.protection.association.RegionAssociable"), Class.forName("[Lcom.sk89q.worldguard.protection.flags.StateFlag;"));

                Object regionManager = getRegionManager.invoke(worldGuard, location.getWorld());
                Object applicableRegionSet = getApplicableRegions.invoke(regionManager, location);

                Object denyState = Class.forName("com.sk89q.worldguard.protection.flags.StateFlag$State").getEnumConstants()[1];

                Field field = defaultFlagClass.getField("PVP");
                Object defaultFlagPvp = field.get(null);

                Object stateFlagArray = Array.newInstance(Class.forName("com.sk89q.worldguard.protection.flags.StateFlag"), 1);
                Array.set(stateFlagArray, 0, defaultFlagPvp);

                if (queryState.invoke(applicableRegionSet, null, stateFlagArray) == denyState) {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }
}
