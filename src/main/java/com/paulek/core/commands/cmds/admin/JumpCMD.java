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

        if (player.getTargetBlock(new HashSet<Material>(), 200).isEmpty()) {

            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_JUMP_AIR));

            return false;
        }

        Location location = player.getTargetBlock(new HashSet<Material>(), 500).getLocation();

        player.teleport(location);

        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_JUMP_YUP));

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
