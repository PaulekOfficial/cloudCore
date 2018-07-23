package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TpaCMD extends Command {

    private static HashMap<UUID, Integer> waiting_to_accept_id = new HashMap<UUID, Integer>();
    private static HashMap<UUID, UUID> waiting_to_accept = new HashMap<UUID, UUID>();

    public TpaCMD(){
        super("tpa", "teleports player to player", "/tpa {player}","core.tpa" ,new String[] {});
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

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        waiting_to_accept.remove(player.getUniqueId());
                        waiting_to_accept_id.remove(player.getUniqueId());
                    }
                }, Config.SETTINGS_TPA_WAITINGTOACCEPT * 20);

                waiting_to_accept_id.put(player.getUniqueId(), id.getTaskId());
                waiting_to_accept.put(player.getUniqueId(), ((Player)sender).getUniqueId());

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

    public static HashMap<UUID, Integer> getWaiting_to_accept_id() {
        return waiting_to_accept_id;
    }

    public static HashMap<UUID, UUID> getWaiting_to_accept() {
        return waiting_to_accept;
    }
}
