package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import common.ColorUtil;
import common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCMD extends Command {

    public DayCMD(Core core) {
        super("day", "makes day", "/day", "core.cmd.day", new String[]{"dzien"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            try {
                long time = 0;
                ((Player) sender).getWorld().setTime(time);
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TIME_SET.replace("{world}", ((Player) sender).getWorld().getName()).replace("{time}", String.valueOf(time))));
            } catch (Exception e) {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_THATSNOTANUMBER));
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }
        return false;
    }
}
