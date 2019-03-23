package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SunCMD extends Command {

    public SunCMD() {
        super("sun", "makes sun", "/sun", "core.cmd.sun", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            player.getWorld().setStorm(false);

            sender.sendMessage(Util.fixColor(Lang.INFO_WEATHER_SUN));

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
