package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.TabCompleterUtils;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            final User user = getCore().getUsersStorage().get(player.getUniqueId());

            if (args.length == 1) {

                if (user.getHome(args[0]) != null) {

                    if (!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                            public void run() {
                                LocationUtil.safeTeleport(getCore().getConfiguration(), user.getHome(args[0]), player);

                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, getCore().getConfiguration().generatorDelay * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        LocationUtil.safeTeleport(getCore().getConfiguration(), user.getHome(args[0]), player);

                        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORT));
                    }


                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_HOME_NOTEXIST.replace("{name}", args[0])));
                }

            } else if (args.length == 0) {

                if (user.getHomes().size() > 1) {

                    String s = "";

                    for (String str : user.getHomes().keySet()) {

                        s += str + ", ";

                    }

                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_MOREHOMES.replace("{homes}", s)));
                    return false;

                }

                if (user.getHome("home") != null) {

                    if (!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                            public void run() {
                                player.teleport(user.getHome("home"));

                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, getCore().getConfiguration().generatorDelay * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        player.teleport(user.getHome("home"));

                        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_TELEPORT));
                    }

                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_DEFAULTNOTEXIST));
                }

            } else {
                sender.sendMessage(getUsage());
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1){
            TabCompleterUtils.getHomes(sender, getCore());
        }
        return new ArrayList<>();
    }

}
