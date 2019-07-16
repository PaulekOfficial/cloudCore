package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RCMD extends Command {

    public RCMD(Core core) {
        super("r", "reply to a message", "/r {message}", "core.cmd.r", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            UUID uuid = ((Player) sender).getUniqueId();
            if (getCore().getPmsStorage().getMessages().containsKey(uuid)) {
                String s = "";
                for (int i = 0; i != args.length; i++) {
                    s += args[i] + " ";
                }
                String message = ColorUtil.fixColor(Lang.INFO_MSG_FORMAT.replace("{message}", s));
                if (Bukkit.getPlayer(getCore().getPmsStorage().getMessages().get(uuid)) != null) {
                    Bukkit.getPlayer(getCore().getPmsStorage().getMessages().get(uuid)).sendMessage(message.replace("{from}", sender.getName()));
                    sender.sendMessage(message.replace("{from}", sender.getName()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        UUID u = p.getUniqueId();
                        if (getCore().getUsersStorage().getUser(u).isSocialSpy()) {
                            String m = ColorUtil.fixColor(Lang.INFO_MSG_SPYFORMAT.replace("{from}", sender.getName()).replace("{to}", Bukkit.getPlayer(getCore().getPmsStorage().getMessages().get(uuid)).getDisplayName()).replace("{message}", s));
                            p.sendMessage(m);
                        }
                    }
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_NOTHINGTOANSWER));
                }
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_PLAYEROFF));
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
