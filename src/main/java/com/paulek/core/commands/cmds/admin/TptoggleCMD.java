package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TptoggleCMD extends Command {

    public TptoggleCMD(Core core) {
        super("tptoogle", "Togle your teleportation", "/tptoggle {on/off}", "core.cmd.tptoogle", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        User user = getCore().getUsersStorage().getUser(((Player) sender).getUniqueId());

        if (args.length == 1) {

            if (user.getSettings().isTptoogle()) {
                user.getSettings().setTptoogle(false);
                sender.sendMessage(Util.fixColor(Lang.INFO_TPTOGGLE_DISABLED));
            } else {
                user.getSettings().setTptoogle(true);
                sender.sendMessage(Util.fixColor(Lang.INFO_TPTOGGLE_ENABLED));
            }

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("on")) {

                if (user.getSettings().isTptoogle()) {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_TPTOGGLE_ENABLED));
                    return false;
                }

                user.getSettings().setTptoogle(true);
                sender.sendMessage(Util.fixColor(Lang.INFO_TPTOGGLE_ENABLED));

            } else if (args[0].equalsIgnoreCase("off")) {

                if (!user.getSettings().isTptoogle()) {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_TPTOGGLE_DISABLED));
                    return false;
                }

                user.getSettings().setTptoogle(false);
                sender.sendMessage(Util.fixColor(Lang.INFO_TPTOGGLE_DISABLED));

            } else {
                sender.sendMessage(getUsage());
            }
        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }
}
