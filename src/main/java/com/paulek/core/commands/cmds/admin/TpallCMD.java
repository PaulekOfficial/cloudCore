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

public class TpallCMD extends Command {

    public TpallCMD(Core core) {
        super("tpall", "teleports all player to yours location", "/tpall", "core.cmd.tpall", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        Location location = ((Player) sender).getLocation();

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (!getCore().getUsersStorage().getUser(p.getUniqueId()).isTpToogle()) {
                p.teleport(location);
            }

        }

        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPALL_TELEPORTED));

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
