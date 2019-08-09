package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import common.ColorUtil;
import common.LocationUtil;
import common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TpaCMD extends Command {

    public TpaCMD(Core core) {
        super("tpa", "teleports player to player", "/tpa {player}", "core.cmd.tpa", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if (args.length >= 1) {

            if (Bukkit.getPlayer(args[0]) != null) {
                final Player player = Bukkit.getPlayer(args[0]);

                if (LocationUtil.isUserTpToogle(player, getCore())) return false;

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                    public void run() {
                        getCore().getTpaStorage().removeToAcceptTpa(player.getUniqueId());
                        getCore().getTpaStorage().cancelTaskTpa(player.getUniqueId());
                    }
                }, getCore().getConfiguration().tpaRejectTime * 20);

                getCore().getTpaStorage().addTaskTpa(player.getUniqueId(), id.getTaskId());
                getCore().getTpaStorage().addToAcceptTpa(player.getUniqueId(), ((Player) sender).getUniqueId());

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_REQUEST.replace("{player}", player.getDisplayName())));

                player.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_REQUESTPLAYER.replace("{player}", ((Player) sender).getDisplayName())));

            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPA_NOPLAYER));
            }

        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            List<String> playerList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getDisplayName());
            }
            return playerList;
        }
        return new ArrayList<>();
    }

}
