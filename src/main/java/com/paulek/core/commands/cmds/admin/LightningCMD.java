package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LightningCMD extends Command {

    public LightningCMD(){
        super("thor", "Create a lightning", "/thor {player}", "core.cmd.thor", new String[]{"lightning"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length == 1){

            if(Bukkit.getPlayer(args[0]) != null){

                Player player = Bukkit.getPlayer(args[0]);

                Location location = player.getLocation();

                location.getWorld().strikeLightning(location);

                sender.sendMessage(Util.fixColor(Lang.INFO_THOR_THORED));

                player.sendMessage(Util.fixColor(Lang.INFO_THOR_PLAYERTHORED));

            } else {

                sender.sendMessage(Util.fixColor(Lang.ERROR_THOR_PLAYEROFFINLE));

                return false;
            }

        } else {
            if(!(sender instanceof Player)){
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }

            Player player = (Player)sender;

            Location location = player.getTargetBlock(null, 2000).getLocation();

            location.getWorld().strikeLightning(location);

        }

        return false;
    }
}
