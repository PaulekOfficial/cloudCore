package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class HomeCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport = new HashMap<UUID, Integer>();

    public HomeCMD(Core core) {
        super("home", "teleports to home", "/home {name}", "core.cmd.home", new String[]{}, core);
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport;
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {

        if (sender instanceof Player) {

            final Player player = (Player) sender;

            final User user = getCore().getUsersStorage().getUser(player.getUniqueId());

            if (args.length == 1) {

                if (user.getHome(args[0]) != null) {

                    if (!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                            public void run() {
                                new LocationUtil(user.getHome(args[0]), player);

                                sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, Config.HOME_DETLY * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        new LocationUtil(user.getHome(args[0]), player);

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                    }


                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_NOTEXIST.replace("{name}", args[0])));
                }

            } else if (args.length == 0) {

                if (user.getHomes().size() > 1) {

                    String s = "";

                    for (String str : user.getHomes().keySet()) {

                        s += str + ", ";

                    }

                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_MOREHOMES.replace("{homes}", s)));
                    return false;

                }

                if (user.getHome("home") != null) {

                    if (!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                            public void run() {
                                player.teleport(user.getHome("home"));

                                sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, Config.HOME_DETLY * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        player.teleport(user.getHome("home"));

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                    }

                } else {
                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DEFAULTNOTEXIST));
                }

            } else {
                sender.sendMessage(getUsage());
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
