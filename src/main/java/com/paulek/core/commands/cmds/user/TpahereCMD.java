package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.TeleportUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TpahereCMD extends Command {

    public TpahereCMD(Core core) {
        super("tpahere", "teleport to you", "/tpahere {player}", "core.cmd.tpahere", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length >= 1) {

            if (Bukkit.getPlayer(args[0]) != null) {
                final Player p = Bukkit.getPlayer(args[0]);

                if (TeleportUtil.hasPlayerTpToogle(player, getCore())) return false;

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                    public void run() {
                        getCore().getTpaStorage().removeToAcceptTpahere(p.getUniqueId());
                        getCore().getTpaStorage().cancelTaskTpahere(p.getUniqueId());
                    }
                }, Config.TPA_WAITINGTIME * 20);

                getCore().getTpaStorage().addTaskTpahere(p.getUniqueId(), id.getTaskId());
                getCore().getTpaStorage().addToAcceptTpahere(p.getUniqueId(), player.getUniqueId());

                sender.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUEST));
                p.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUESTPLAYER));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
            }

        }

        return false;
    }
}
