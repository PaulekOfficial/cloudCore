package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.data.TpaStorage;
import com.paulek.core.basic.data.UserStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Config;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TpahereCMD extends Command {

    public TpahereCMD() {
        super("tpahere", "teleport to you", "/tpahere {player}", "core.tpahere", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player)sender;

        if(args.length >= 1) {

            if (Bukkit.getPlayer(args[0]) != null) {
                final Player p = Bukkit.getPlayer(args[0]);

                if(UserStorage.getUser(p.getUniqueId()).getSettings().isTptoogle()){

                    sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));

                    return false;
                }

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        TpaStorage.removeToAcceptTpahere(p.getUniqueId());
                        TpaStorage.cancelTaskTpahere(p.getUniqueId());
                    }
                }, Config.SETTINGS_TPA_WAITINGTOACCEPT * 20);

                TpaStorage.addTaskTpahere(p.getUniqueId(), id.getTaskId());
                TpaStorage.addToAcceptTpahere(p.getUniqueId(), player.getUniqueId());

                sender.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUEST));
                p.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUESTPLAYER));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
            }

        }

        return false;
    }
}
