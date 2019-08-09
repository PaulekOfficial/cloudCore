package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import common.ColorUtil;
import common.LocationUtil;
import common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCMD extends Command {

    public TpCMD(Core core) {
        super("tp", "teleport player to target", "/tp (nick, X) (target, Y) (Z)", "core.cmd.tp", new String[]{"tp"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(this.getUsage());
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = null;
                if (Bukkit.getPlayerExact(args[0]) != null) {
                    player = Bukkit.getPlayerExact(args[0]);

                    if (LocationUtil.isUserTpToogle(player, getCore())){
                        return false;
                    }

                    ((Player) sender).teleport(player.getLocation());
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TP_INFO.replace("{player}", player.getDisplayName())));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TP_NOTONLINE));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        if (args.length == 2) {
            Player player1 = null;
            Player player2 = null;
            if ((Bukkit.getPlayerExact(args[0]) != null) && (Bukkit.getPlayerExact(args[1]) != null)) {
                player1 = Bukkit.getPlayerExact(args[0]);

                if (LocationUtil.isUserTpToogle(player1, getCore())){
                    return false;
                }

                player2 = Bukkit.getPlayerExact(args[1]);
                player1.teleport(player2.getLocation());
                player1.sendMessage(ColorUtil.fixColor(Lang.INFO_TP_PLAYERTOPLAYER.replace("{player}", player1.getDisplayName()).replace("{target}", player2.getDisplayName())));
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TP_NOTONLINE));
            }
        }
        if (args.length == 3) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                double x;
                double y;
                double z;
                try {
                    x = Double.valueOf(args[0]);
                    y = Double.valueOf(args[1]);
                    z = Double.valueOf(args[2]);
                    Location loc = new Location(player.getWorld(), x, y, z);
                    player.getWorld().loadChunk((int) x, (int) y);
                    player.teleport(loc);
                    String a = Lang.INFO_TP_CORDINATES.replace("{x}", String.valueOf(loc.getX()));
                    a = a.replace("{y}", String.valueOf(loc.getY()));
                    a = a.replace("{z}", String.valueOf(loc.getZ()));
                    player.sendMessage(ColorUtil.fixColor(a));
                } catch (Exception e) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TP_NOTCORDINATES));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        return false;
    }
}
