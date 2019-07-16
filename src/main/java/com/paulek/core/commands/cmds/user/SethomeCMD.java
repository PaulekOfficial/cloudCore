package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SethomeCMD extends Command {

    private static HashMap<String, Integer> groups_amount = new HashMap<String, Integer>();

    public SethomeCMD(Core core) {
        super("sethome", "sets a home", "/sethome {name}", "core.cmd.sethome", new String[]{}, core);
    }

    public static void loadGroups() {

        for (String s : Config.HOME_AMOUNT) {

            String[] a = s.split(" ");

            groups_amount.put(a[0], Integer.valueOf(a[1]));

        }

    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            if (args.length == 1) {

                Player player = (Player) sender;

                int amount = 1;

                for (String s : groups_amount.keySet()) {
                    if (getCore().getPermission().playerInGroup(player, s)) {
                        amount = groups_amount.get(s);
                    }
                }

                Location location = player.getLocation();

                User user = getCore().getUsersStorage().getUser(player.getUniqueId());

                if (!sender.hasPermission("core.cmd.home.bypasslimit")) {
                    if (user.getHomes().size() >= amount) {

                        sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_HOME_CANNOTSET));

                        return false;
                    }
                }

                if (user.getHome(args[0]) == null) {
                    user.addHome(args[0], location);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_SET.replace("{name}", args[0])));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_HOME_EXIST));
                }

            } else if (args.length == 0) {

                Player player = (Player) sender;

                Location location = player.getLocation();

                User user = getCore().getUsersStorage().getUser(player.getUniqueId());

                user.addHome("home", location);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_SETNONAME));
            } else {
                sender.sendMessage(getUsage());
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

}
