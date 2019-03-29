package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCMD extends Command {

    public ChatCMD(Core core) {
        super("chat", "chat manipulation", "/chat (on,off,clear)", "core.cmd.chat", new String[]{"chatmanipulation", "czat"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("off")) {
                if (!getCore().isChatEnabled()) {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLED));
                    return false;
                }
                getCore().setChatEnabled(false);
                sender.sendMessage(Util.fixColor(Lang.INFO_CHAT_SETOFF));
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_BRODCASTOFF));
            }
            if (args[0].equalsIgnoreCase("on")) {
                if (getCore().isChatEnabled()) {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_CHAT_ENABLED));
                    return false;
                }
                getCore().setChatEnabled(true);
                sender.sendMessage(Util.fixColor(Lang.INFO_CHAT_SETON));
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_BRODCASTON));
            }
            if (args[0].equalsIgnoreCase("clear")) {
                for (int i = 0; i < 100; i++) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(" ");
                    }
                }
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_CLEAR.replace("{player}", sender.getName())));
            }
        } else {
            sender.sendMessage(this.getUsage());
        }
        return false;
    }
}
