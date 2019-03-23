package com.paulek.core.commands.cmds.admin;

import com.paulek.core.basic.data.Users;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MsgspyCMD extends Command {

    public MsgspyCMD() {
        super("spymsg", "spy private messages", "/mspy {on, off}", "core.cmd.mspy", new String[] {"mspy", "socialspy"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            if (args[0].equalsIgnoreCase("on")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (!Users.getUser(uuid).getSettings().isSocialspy()) {
                    Users.getUser(uuid).getSettings().setSocialspy(true);
                    sender.sendMessage(Util.fixColor(Lang.INFO_MSG_SPY));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_ALREADY));
                }
            }
            if (args[0].equalsIgnoreCase("off")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (Users.getUser(uuid).getSettings().isSocialspy()) {
                    Users.getUser(uuid).getSettings().setSocialspy(false);
                    sender.sendMessage(Util.fixColor(Lang.INFO_MSG_DISABLE));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_ALREADYNO));
                }
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
