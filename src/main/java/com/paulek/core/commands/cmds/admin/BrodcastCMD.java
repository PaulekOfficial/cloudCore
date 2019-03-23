package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrodcastCMD extends Command {

    public BrodcastCMD() {
        super("brodcast", "brodcast a message", "/bc {title, action, chat} {message}", "core.cmd.bc", new String[]{"brodcast"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("title")) {
                String s;
                int i;
                if (args.length > 1) {
                    s = "";
                    for (i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Util.sendTitle(p, Util.fixColor(Lang.INFO_BRODCAST_TITLE), Util.fixColor(s), 5, 60, 5);
                    }
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("action")) {
                String s;
                int i;
                if (args.length > 1) {
                    s = "";
                    for (i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Util.sendActionbar(p, Util.fixColor(s));
                    }
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("chat")) {
                String s = "";
                if (args.length > 1) {
                    for (int i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_BRODCAST_CHAT).replace("{message}", s));
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else {
                sender.sendMessage(getUsage());
                return false;
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
