package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.commands.cmds.admin.VanishCMD;
import com.paulek.core.commands.cmds.user.SpawnCMD;
import com.paulek.core.commands.cmds.user.TpacceptCMD;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.UUID;

public class StorageListeners implements Listener {

    private Core core;

    public StorageListeners(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        if (VanishCMD.getList().containsKey(uuid)) {

            BukkitTask id = VanishCMD.getList().get(uuid);

            Bukkit.getScheduler().cancelTask(id.getTaskId());

            VanishCMD.getList().remove(uuid);

        }

        core.getPrivateMessagesStorage().delete(uuid);



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

}
