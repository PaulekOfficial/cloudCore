package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JumpCMD extends Command {

    public JumpCMD(Core core) {
        super("jump", "teleport to crosshair", "/j", "core.cmd.jump", new String[]{"jumpto", "j"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        Player player = (Player) sender;

        if (player.getTargetBlock(null, 200).isEmpty()) {

            sender.sendMessage(Util.fixColor(Lang.ERROR_JUMP_AIR));

            return false;
        }

        Location location = player.getTargetBlock(null, 500).getLocation();

        new LocationUtil(location, player);

        sender.sendMessage(Util.fixColor(Lang.INFO_JUMP_YUP));

        return false;
    }
}
