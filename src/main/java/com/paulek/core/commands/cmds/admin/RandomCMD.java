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
            if ((player.getTargetBlock(null, 1).getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (player.getTargetBlock(null, 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock(null, 1).getLocation();
                if (getCore().getRtpsStorage().getList().contains(loc)) {
                    getCore().getRtpsStorage().removeFromList(loc);
                    Config.RTP_BUTTONLIST = null;
                    Config.RTP_BUTTONLIST = getCore().getRtpsStorage().getStringLoc();
                    getCore().getConfiguration().saveConfig();
                    getCore().getConfiguration().reloadConfig();
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
            if ((player.getTargetBlock(null, 1).getType() == XMaterial.LEGACY_WOOD_BUTTON.parseMaterial()) || (player.getTargetBlock(null, 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock(null, 1).getLocation();
                if (getCore().getRtpsStorage().getList().contains(loc)) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_RANDOMTP_EXIST));
                    return false;
                }
                Location button = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                getCore().getRtpsStorage().addToList(button);
                Config.RTP_BUTTONLIST = null;
                Config.RTP_BUTTONLIST = getCore().getRtpsStorage().getStringLoc();
                getCore().getConfiguration().saveConfig();
                getCore().getConfiguration().reloadConfig();
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
