package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.data.TpaStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.TeleportUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Config;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TpacceptCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport_map = new HashMap<UUID, Integer>();

    public TpacceptCMD(){
        super("tpaccept", "accepts a teleport to player", "/tpaccept", "core.tpaccept", new String[]{});
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player)sender;
        UUID uuid = player.getUniqueId();

        if(TpaStorage.getToAcceptTpa(uuid) != null){

            if(Bukkit.getPlayer(TpaStorage.getToAcceptTpa(uuid)) != null){

                final Player to_teleport = Bukkit.getPlayer(TpaStorage.getToAcceptTpa(uuid));

                sender.sendMessage(Util.fixColor(Lang.INFO_TPA_ACCEPT));

                to_teleport.sendMessage(Util.fixColor(Lang.INFO_TPA_ACCEPTED));

                TpaStorage.cancelTaskTpa(to_teleport.getUniqueId());
                TpaStorage.removeToAcceptTpa(uuid);

                BukkitTask id;

                final Location location = player.getLocation();

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {

                        if(Bukkit.getPlayer(to_teleport.getUniqueId()) != null) new TeleportUtil(location, to_teleport);

                        if(Bukkit.getPlayer(to_teleport.getUniqueId()) != null) to_teleport.sendMessage(Util.fixColor(Lang.INFO_TPA_TELEPORT));

                        to_teleport_map.remove(to_teleport.getUniqueId());

                    }
                }, Config.SETTINGS_TPA_DETLY * 20);

                to_teleport_map.put(to_teleport.getUniqueId(), id.getTaskId());

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPA_NOPLAYER));
            }

        } else if (TpaStorage.getToAcceptTpahere(uuid) != null) {

            if(Bukkit.getPlayer(TpaStorage.getToAcceptTpahere(uuid)) != null) {

                final Player player1 = Bukkit.getPlayer(TpaStorage.getToAcceptTpahere(uuid));

                sender.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_ACCEPTED));

                player1.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_ACCEPT));

                TpaStorage.cancelTaskTpahere(player.getUniqueId());
                TpaStorage.removeToAcceptTpahere(uuid);

                BukkitTask id;

                final Location location = Bukkit.getPlayer(TpaStorage.getToAcceptTpahere(player1.getUniqueId())).getLocation();

                id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    public void run() {

                        if (Bukkit.getPlayer(player1.getUniqueId()) != null) new TeleportUtil(location, player1);

                        if (Bukkit.getPlayer(player1.getUniqueId()) != null)
                            player1.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_TELEPORT));

                        to_teleport_map.remove(player1.getUniqueId());

                    }
                }, Config.SETTINGS_TPA_DETLY * 20);

                to_teleport_map.put(player1.getUniqueId(), id.getTaskId());
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
            }

        } else {
            sender.sendMessage(Util.fixColor(Lang.ERROR_TPACCEPT_NOTHINGTOACCEPT));
        }

        return false;
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport_map;
    }
}
