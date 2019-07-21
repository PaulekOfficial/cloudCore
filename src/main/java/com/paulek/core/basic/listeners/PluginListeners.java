package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.commands.cmds.admin.VanishCMD;
import com.paulek.core.commands.cmds.user.SpawnCMD;
import com.paulek.core.commands.cmds.user.TpacceptCMD;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PluginListeners implements Listener {

    private List<String> cenzored;
    private String replacecenzor;
    private HashMap<UUID, Long> slowdon;
    private HashMap<String, String> groups_format;

    private Core core;

    public PluginListeners(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        cenzored = core.getConfiguration().chatCenzoredList;
        replacecenzor = core.getConfiguration().chatCenzorReplace;
        slowdon = new HashMap<UUID, Long>();
        groups_format = new HashMap<String, String>();
        loadGroups();
    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        if (VanishCMD.getList().containsKey(uuid)) {

            BukkitTask id = VanishCMD.getList().get(uuid);

            Bukkit.getScheduler().cancelTask(id.getTaskId());

            VanishCMD.getList().remove(uuid);

        }

        if (TpacceptCMD.getTo_teleport().containsKey(uuid)) {
            Bukkit.getScheduler().cancelTask(TpacceptCMD.getTo_teleport().get(uuid));
            TpacceptCMD.getTo_teleport().remove(uuid);
        }

        if (core.getTpaStorage().getToAcceptTpahere(uuid) != null) {
            core.getTpaStorage().removeToAcceptTpahere(uuid);
            core.getTpaStorage().cancelTaskTpahere(uuid);
        }

        if (core.getTpaStorage().getToAcceptTpa(uuid) != null) {
            core.getTpaStorage().removeToAcceptTpa(uuid);
            core.getTpaStorage().cancelTaskTpa(uuid);
        }

        if (SpawnCMD.getIn_detly().containsKey(uuid)) {
            Bukkit.getScheduler().cancelTask(SpawnCMD.getIn_detly().get(uuid));
            SpawnCMD.getIn_detly().remove(uuid);
        }

        if (core.getCombatStorage().isMarked(uuid)) {
            core.getCombatStorage().unmark(uuid);

            if (core.getConfiguration().combatAnnouncement) {

                String s = ColorUtil.fixColor(Lang.INFO_COMBAT_BRODCASTLOGOUT).replace("{player}", event.getPlayer().getName()).replace("{health}", (int) event.getPlayer().getHealth() + "♥");

                Bukkit.broadcastMessage(s);

            }

            if (core.getConfiguration().combatKillOnQuit) event.getPlayer().setHealth(0.0);

        }
    }

    @EventHandler
    public void onPreLogin(org.bukkit.event.player.AsyncPlayerPreLoginEvent event) {
        //TODO Whitelist
//        if (Config.WHITELIST_ENABLE) {
//            if (!Config.WHITELIST_ALLOWEDPLAYERS.contains(event.getName())) {
//
//                event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ColorUtil.fixColor(Config.WHITELIST_MOD));
//
//            }
//        }
    }

    @EventHandler
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        for (String s : groups_format.keySet()) {
            if (core.getPermission().playerInGroup(event.getPlayer(), s)) {
                String format;
                if (core.getChat().getPrimaryGroup(event.getPlayer()) != null) {
                    if (!event.getPlayer().hasPermission("core.chat.color")) {
                        format = ColorUtil.fixColor(groups_format.get(s).replace("{displayname}", core.getChat().getGroupPrefix(event.getPlayer().getWorld(), core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName())).replace("{message}", event.getMessage());
                    } else {
                        format = ColorUtil.fixColor(groups_format.get(s).replace("{displayname}", core.getChat().getGroupPrefix(event.getPlayer().getWorld(), core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName()).replace("{message}", event.getMessage()));
                    }
                    event.setFormat(format);
                }
            }
        }

        if (!core.isChatEnabled()) {
            if (!event.getPlayer().hasPermission("core.chat.writewhendisabled")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }
        if (!core.getConfiguration().chatModuleEnabled) {
            if (!event.getPlayer().hasPermission("core.chat.disable")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }

        if (core.getConfiguration().combatAnnouncement) {
            if (!event.getPlayer().hasPermission("core.chat.nouppercase")) {
                event.setMessage(event.getMessage().toLowerCase());
            }
        }

        if (core.getConfiguration().chatSlowdown > 0) {
            if (!event.getPlayer().hasPermission("core.chat.noslowdown")) {
                UUID uuid = event.getPlayer().getUniqueId();
                if (slowdon.containsKey(uuid)) {
                    if (((System.currentTimeMillis() - slowdon.get(uuid)) / 1000) < core.getConfiguration().chatSlowdown) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_SLOWDOWN));
                    } else {
                        slowdon.put(uuid, System.currentTimeMillis());
                    }
                } else {
                    slowdon.put(uuid, System.currentTimeMillis());
                }
            }
        }
        if (core.getConfiguration().chatCenzor) {
            if (!event.getPlayer().hasPermission("core.chat.nocenzor")) {
                for (String s : cenzored) {
                    event.setMessage(event.getMessage().replaceAll(s, replacecenzor));
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

//        Player player = event.getPlayer();
//        Block block = event.getBlock();
//        ItemStack tool = player.getItemInHand();
//
//        DropMask dropMask = core.getDrops().getMask(block.getType().name());
//
//        if(dropMask != null) {
//
//            dropMask.breakBlock(player, tool, block);
//            //TODO Drop items
//            //if(dropMask.isDropped()){
//            //    event.setDropItems(false);
//            //}
//
//        }
    }

    private void loadGroups() {

        for (String s : core.getConfiguration().chatFormating.keySet()) {

            String format = core.getConfiguration().chatFormating.get(s);

            groups_format.put(s, format);

        }

    }

}
