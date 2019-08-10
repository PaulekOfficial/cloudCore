package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCMD extends Command {

    public SetSpawnCMD(Core core) {
        super("setspawn", "sets spawn", "/setspawn {name}", "core.cmd.setspawn", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if (args.length > 1){
            sender.sendMessage(ColorUtil.fixColor(getUsage()));
        }

        if(args.length == 1){
            Location location = ((Player) sender).getLocation();
            String name = args[0];
            getCore().getSpawnsStorage().addNewSpawn(name, location);
            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SPAWN_SET));
        }

        Location location = ((Player) sender).getLocation();

        getCore().getSpawnsStorage().addNewSpawn("default", location);

        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SPAWN_SET));

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
