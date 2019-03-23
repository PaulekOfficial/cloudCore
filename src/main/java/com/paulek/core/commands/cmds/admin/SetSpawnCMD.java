package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCMD extends Command {

    public SetSpawnCMD() {
        super("setspawn", "sets spawn", "/setspawn", "core.cmd.setspawn", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        Location location = ((Player)sender).getLocation();

        location.getWorld().setSpawnLocation(location);

        Config.SPAWN_WORLD = location.getWorld().getName();
        Config.SPAWN_BLOCK_X = location.getX();
        Config.SPAWN_BLOCK_Z = location.getZ();
        Config.SPAWN_BLOCK_Y = location.getY();
        Config.SPAWN_YAW = location.getYaw();


        Config.saveConfig();
        Config.reloadConfig();

        sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_SET));

        return false;
    }
}
