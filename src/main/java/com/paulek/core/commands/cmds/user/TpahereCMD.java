package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TpahereCMD extends Command {

    private static HashMap<UUID, Integer> waiting_to_accept_id = new HashMap<UUID, Integer>();
    private static HashMap<UUID, UUID> waiting_to_accept = new HashMap<UUID, UUID>();

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

                if(!UserStorage.getUser(p.getUniqueId()).isTptoogle()){

                    sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));

                    return false;
                }

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {
                        waiting_to_accept.remove(p.getUniqueId());
                        waiting_to_accept_id.remove(p.getUniqueId());
                    }
                }, Config.SETTINGS_TPA_WAITINGTOACCEPT * 20);

                waiting_to_accept_id.put(p.getUniqueId(), id.getTaskId());
                waiting_to_accept.put(p.getUniqueId(), player.getUniqueId());

                sender.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUEST));
                p.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_REQUESTPLAYER));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
            }

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
