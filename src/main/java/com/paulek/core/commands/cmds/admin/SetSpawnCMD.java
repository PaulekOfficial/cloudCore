package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCMD extends Command {

    public SetSpawnCMD() {
        super("setspawn", "sets spawn", "/setspawn", "core.setspawn", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        Location location = ((Player)sender).getLocation();

        location.getWorld().setSpawnLocation(location);

        Config.SETTINGS_SPAWN_WORLD = location.getWorld().getName();
        Config.SETTINGS_SPAWN_BLOCKX = location.getX();
        Config.SETTINGS_SPAWN_BLOCKZ = location.getZ();
        Config.SETTINGS_SPAWN_BLOCKY = location.getY();
        Config.SETTINGS_SPAWN_YAW = location.getYaw();


        Config.saveConfig();
        Config.reloadConfig();

        sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_SET));

        return false;
    }
}
