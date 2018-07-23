package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.RandomtpStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class RandomCMD extends Command {

    public RandomCMD() {
        super("randomtp", "make a randomtp button", "/rtp {setbutton, removebutton}","core.rtp" ,new String[] {"rtp"});
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
            if ((player.getTargetBlock((Set<Material>) null, 1).getType() == Material.WOOD_BUTTON) || (player.getTargetBlock((Set<Material>) null, 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock((Set<Material>) null, 1).getLocation();
                if (RandomtpStorage.getList().contains(loc)) {
                    RandomtpStorage.removeFromList(loc);
                    Config.SETTINGS_RANDOMTELEPORT_BUTTONS = null;
                    Config.SETTINGS_RANDOMTELEPORT_BUTTONS = RandomtpStorage.getStringLoc();
                    Config.saveConfig();
                    Config.reloadConfig();
                    sender.sendMessage(Util.fixColor(Lang.INFO_RANDOMTP_REMOVED));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_RANDOMTP_NOTREMOVED));
                }
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_RANDOMTP_NOTLOOKING));
                return false;
            }
        } else if (args[0].equalsIgnoreCase("setbutton")) {
            Player player = (Player) sender;
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }
            if ((player.getTargetBlock((Set<Material>) null, 1).getType() == Material.WOOD_BUTTON) || (player.getTargetBlock((Set<Material>) null, 5).getType() == Material.STONE_BUTTON)) {
                Location loc = player.getTargetBlock((Set<Material>) null, 1).getLocation();
                if (RandomtpStorage.getList().contains(loc)) {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_RANDOMTP_EXIST));
                    return false;
                }
                Location button = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                RandomtpStorage.addToList(button);
                Config.SETTINGS_RANDOMTELEPORT_BUTTONS = null;
                Config.SETTINGS_RANDOMTELEPORT_BUTTONS = RandomtpStorage.getStringLoc();
                Config.saveConfig();
                Config.reloadConfig();
                sender.sendMessage(Util.fixColor(Lang.INFO_RANDOMTP_CREATED));
                return true;
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_RANDOMTP_NOTLOOKING));
                return false;
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
