package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCMD extends Command {

    public HelpopCMD(Core core) {
        super("helpop", "send a message to moderators", "/helpop (message)", "core.cmd.helpop", new String[]{"pomocy"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ColorUtil.fixColor(this.getUsage()));
            return false;
        }
        String message = "";
        for (int i = 0; i < args.length; i++) {
            message += args[i] + " ";
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("core.helpop.recive")) {
                p.sendMessage(ColorUtil.fixColor(Lang.INFO_HELPOP_FORMAT.replace("{player}", sender.getName()).replace("{message}", message)));
            }
        }
        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HELPOP_SENDED));
        return false;
    }
}
