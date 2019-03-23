package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.data.TpaStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.TeleportUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TpaCMD extends Command {

    public TpaCMD(){
        super("tpa", "teleports player to player", "/tpa {player}","core.cmd.tpa" ,new String[] {});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if(args.length >= 1){

            if(Bukkit.getPlayer(args[0]) != null){
                final Player player = Bukkit.getPlayer(args[0]);

                if(TeleportUtil.hasPlayerTpToogle(player)) return false;

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        TpaStorage.removeToAcceptTpa(player.getUniqueId());
                        TpaStorage.cancelTaskTpa(player.getUniqueId());
                    }
                }, Config.TPA_WAITINGTIME * 20);

                TpaStorage.addTaskTpa(player.getUniqueId(), id.getTaskId());
                TpaStorage.addToAcceptTpa(player.getUniqueId(), ((Player)sender).getUniqueId());

                sender.sendMessage(Util.fixColor(Lang.INFO_TPA_REQUEST.replace("{player}", player.getDisplayName())));

                player.sendMessage(Util.fixColor(Lang.INFO_TPA_REQUESTPLAYER.replace("{player}", ((Player)sender).getDisplayName())));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPA_NOPLAYER));
            }

        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }
}
