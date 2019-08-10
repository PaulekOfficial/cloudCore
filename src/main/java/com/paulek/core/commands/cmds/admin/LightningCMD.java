package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LightningCMD extends Command {

    public LightningCMD(Core core) {
        super("thor", "Create a lightning", "/thor {player}", "core.cmd.thor", new String[]{"lightning"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 1) {

            if (Bukkit.getPlayer(args[0]) != null) {

                Player player = Bukkit.getPlayer(args[0]);

                Location location = player.getLocation();

                location.getWorld().strikeLightning(location);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_THOR_THORED));

                player.sendMessage(ColorUtil.fixColor(Lang.INFO_THOR_PLAYERTHORED));

            } else {

                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_THOR_PLAYEROFFINLE));

                return false;
            }

        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }

            Player player = (Player) sender;

            Location location = player.getTargetBlock(new HashSet<Material>(), 2000).getLocation();

            location.getWorld().strikeLightning(location);

        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            List<String> playerList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getDisplayName());
            }
            return playerList;
        }

        return new ArrayList<>();
    }

}
