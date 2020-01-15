package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.Pair;
import com.paulek.core.basic.TeleportRequstType;
import com.paulek.core.basic.TriplePackage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TpacceptCMD extends Command {

    public TpacceptCMD(Core core) {
        super("tpaccept", "accepts a teleport to player", "/tpaccept", "core.cmd.tpaccept", new String[]{}, core);
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        TriplePackage<UUID, LocalDateTime, TeleportRequstType> dataPacket = getCore().getTeleportRequestsStorage().get(uuid);
        if (dataPacket != null) {

            if(dataPacket.getR().equals(TeleportRequstType.TPA)) {
                Player p = Bukkit.getPlayer(dataPacket.getT());
                if(p == null) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPACCEPT_NOTHINGTOACCEPT));
                    return true;
                }
                int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(getCore().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        player.teleport(p.getLocation());
                        player.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_TELEPORT));
                    }
                }, getCore().getConfiguration().teleportDelay * 20);
                getCore().getTeleportRequestsStorage().addTeleportDelay(player.getUniqueId(), taskId);
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_ACCEPT));
                p.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_ACCEPTED));
                getCore().getTeleportRequestsStorage().delete(uuid);
            }
            if(dataPacket.getR().equals(TeleportRequstType.TPAHERE)) {
                Player p = Bukkit.getPlayer(dataPacket.getT());
                if(p == null) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPACCEPT_NOTHINGTOACCEPT));
                    return true;
                }
                int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(getCore().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(player.getLocation());
                        p.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_TELEPORT));
                    }
                }, getCore().getConfiguration().teleportDelay * 20);
                getCore().getTeleportRequestsStorage().addTeleportDelay(p.getUniqueId(), taskId);
                p.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_ACCEPT));
                player.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_ACCEPTED));
                getCore().getTeleportRequestsStorage().delete(uuid);
            }

        } else {
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPACCEPT_NOTHINGTOACCEPT));
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
