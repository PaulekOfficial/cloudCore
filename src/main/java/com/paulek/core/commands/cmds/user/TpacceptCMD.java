package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TpacceptCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport_map = new HashMap<UUID, Integer>();

    public TpacceptCMD(Core core) {
        super("tpaccept", "accepts a teleport to player", "/tpaccept", "core.cmd.tpaccept", new String[]{}, core);
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport_map;
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (getCore().getTpaStorage().getToAcceptTpa(uuid) != null) {

            if (Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpa(uuid)) != null) {

                final Player to_teleport = Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpa(uuid));

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_ACCEPT));

                to_teleport.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_ACCEPTED));

                getCore().getTpaStorage().cancelTaskTpa(to_teleport.getUniqueId());
                getCore().getTpaStorage().removeToAcceptTpa(uuid);

                BukkitTask id;

                final Location location = player.getLocation();

                id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                    public void run() {

                        if (Bukkit.getPlayer(to_teleport.getUniqueId()) != null)
                            new LocationUtil(location, to_teleport);

                        if (Bukkit.getPlayer(to_teleport.getUniqueId()) != null)
                            to_teleport.sendMessage(ColorUtil.fixColor(Lang.INFO_TPA_TELEPORT));

                        to_teleport_map.remove(to_teleport.getUniqueId());

                    }
                }, Config.TPA_DETLY * 20);

                to_teleport_map.put(to_teleport.getUniqueId(), id.getTaskId());

            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPA_NOPLAYER));
            }

        } else if (getCore().getTpaStorage().getToAcceptTpahere(uuid) != null) {

            if (Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpahere(uuid)) != null) {

                final Player player1 = Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpahere(uuid));

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_ACCEPTED));

                player1.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_ACCEPT));

                getCore().getTpaStorage().cancelTaskTpahere(player.getUniqueId());
                getCore().getTpaStorage().removeToAcceptTpahere(uuid);

                BukkitTask id;

                final Location location = Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpahere(player1.getUniqueId())).getLocation();

                id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                    public void run() {

                        if (Bukkit.getPlayer(player1.getUniqueId()) != null) new LocationUtil(location, player1);

                        if (Bukkit.getPlayer(player1.getUniqueId()) != null)
                            player1.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_TELEPORT));

                        to_teleport_map.remove(player1.getUniqueId());

                    }
                }, Config.TPA_DETLY * 20);

                to_teleport_map.put(player1.getUniqueId(), id.getTaskId());
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
            }

        } else {
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPACCEPT_NOTHINGTOACCEPT));
        }

        return false;
    }
}
