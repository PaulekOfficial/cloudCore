package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SunCMD extends Command {

    public SunCMD(Core core) {
        super("sun", "makes sun", "/sun", "core.cmd.sun", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            player.getWorld().setStorm(false);

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_SUN));

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
