package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import common.ColorUtil;
import common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherCMD extends Command {

    public WeatherCMD(Core core) {
        super("weather", "set the wheater", "/weather (clear,rain)", "core.cmd.weather", new String[]{"pogoda"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(this.getUsage());
        }
        if (args.length > 1) {
            try {
                World world = Bukkit.getWorld(args[1]);
                if (args[0].equalsIgnoreCase("clear")) {
                    world.setStorm(false);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_CLEAR));
                } else if (args[0].equalsIgnoreCase("rain")) {
                    world.setStorm(true);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_STORM));
                } else if (args[0].equalsIgnoreCase("sun")) {
                    world.setStorm(false);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_CLEAR));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(this.getUsage()));
                }
            } catch (Exception e) {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_WEATHER_NOWORLD));
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("clear")) {
                    player.getWorld().setStorm(false);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_CLEAR));
                } else if (args[0].equalsIgnoreCase("rain")) {
                    player.getWorld().setStorm(true);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_STORM));
                } else if (args[0].equalsIgnoreCase("sun")) {
                    player.getWorld().setStorm(false);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WEATHER_CLEAR));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(this.getUsage()));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        return false;
    }
}
