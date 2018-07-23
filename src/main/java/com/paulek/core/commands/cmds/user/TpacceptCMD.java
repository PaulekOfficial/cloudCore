package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.TeleportUtil;
import com.paulek.core.utils.Util;
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

        if(TpaCMD.getWaiting_to_accept().containsKey(uuid)){

            if(Bukkit.getPlayer(TpaCMD.getWaiting_to_accept().get(uuid)) != null){

                final Player to_teleport = Bukkit.getPlayer(TpaCMD.getWaiting_to_accept().get(uuid));

                sender.sendMessage(Util.fixColor(Lang.INFO_TPA_ACCEPT));

                to_teleport.sendMessage(Util.fixColor(Lang.INFO_TPA_ACCEPTED));

                if(TpaCMD.getWaiting_to_accept_id().containsKey(to_teleport.getUniqueId())) {
                    Bukkit.getScheduler().cancelTask(TpaCMD.getWaiting_to_accept_id().get(to_teleport.getUniqueId()));
                    TpaCMD.getWaiting_to_accept_id().remove(to_teleport.getUniqueId());
                }
                TpaCMD.getWaiting_to_accept().remove(uuid);

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

        } else if (TpahereCMD.getWaiting_to_accept().get(uuid) != null) {

            if(Bukkit.getPlayer(TpahereCMD.getWaiting_to_accept().get(uuid)) != null) {

                final Player player1 = Bukkit.getPlayer(TpahereCMD.getWaiting_to_accept().get(uuid));

                sender.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_ACCEPTED));

                player1.sendMessage(Util.fixColor(Lang.INFO_TPAHERE_ACCEPT));

                if (TpahereCMD.getWaiting_to_accept_id().containsKey(player.getUniqueId())) {
                    Bukkit.getScheduler().cancelTask(TpahereCMD.getWaiting_to_accept_id().get(player.getUniqueId()));
                    TpahereCMD.getWaiting_to_accept_id().remove(player.getUniqueId());
                }
                TpahereCMD.getWaiting_to_accept().remove(uuid);

                BukkitTask id;

                final Location location = Bukkit.getPlayer(TpahereCMD.getWaiting_to_accept().get(player1.getUniqueId())).getLocation();

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
