package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.XMaterial;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class RandomCMD extends Command {

    public RandomCMD(Core core) {
        super("randomtp", "make a randomtp button", "/rtp {setbutton, removebutton}", "core.cmd.rtp", new String[]{"rtp"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return true;
        }
        if (args[0].equalsIgnoreCase("removebutton")) {
            Player player = (Player) sender;
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }
            if ((player.getTargetBlock(new HashSet<Material>(), 1).getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (player.getTargetBlock(new HashSet<Material>(), 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock(new HashSet<Material>(), 1).getLocation();
                if (getCore().getRtpsStorage().getButtons().contains(loc)) {
                    getCore().getRtpsStorage().remove(loc);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_RANDOMTP_REMOVED));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_RANDOMTP_NOTREMOVED));
                }
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_RANDOMTP_NOTLOOKING));
                return false;
            }
        } else if (args[0].equalsIgnoreCase("setbutton")) {
            Player player = (Player) sender;
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }
            if ((player.getTargetBlock(new HashSet<Material>(), 1).getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (player.getTargetBlock(new HashSet<Material>(), 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock(new HashSet<Material>(), 1).getLocation();
                if (getCore().getRtpsStorage().getButtons().contains(loc)) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_RANDOMTP_EXIST));
                    return false;
                }
                Location button = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                getCore().getRtpsStorage().add(button);
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_RANDOMTP_CREATED));
                return true;
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_RANDOMTP_NOTLOOKING));
                return false;
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
