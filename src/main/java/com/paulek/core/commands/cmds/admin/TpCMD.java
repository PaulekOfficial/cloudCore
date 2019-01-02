package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.TeleportUtil;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCMD extends Command{

    public TpCMD(){
        super("tp", "teleport player to target", "/tp (nick, X) (target, Y) (Z)", "core.command.tp", new String[] {"tp"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
//        if(args.length < 1){
//            sender.sendMessage(this.getUsage());
//        }
//        if(args.length == 1){
//            if(sender instanceof Player) {
//                Player player = null;
//                if (Bukkit.getPlayerExact(args[0]) != null) {
//                    player = Bukkit.getPlayerExact(args[0]);
//
//                    if(UserStorage.getUser(player.getUniqueId()).isTptoogle()){
//
//                        sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));
//
//                        return false;
//                    }
//
//                    new TeleportUtil(player.getLocation(), (Player)sender);
//                    sender.sendMessage(Util.fixColor(Lang.INFO_TP_INFO.replace("{player}", player.getDisplayName())));
//                } else {
//                    sender.sendMessage(Util.fixColor(Lang.ERROR_TP_NOTONLINE));
//                }
//            } else {
//                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//            }
//        }
//        if(args.length == 2){
//            Player player1 = null;
//            Player player2 = null;
//            if ((Bukkit.getPlayerExact(args[0]) != null) && (Bukkit.getPlayerExact(args[1]) != null)) {
//                player1 = Bukkit.getPlayerExact(args[0]);
//
//                if(UserStorage.getUser(player1.getUniqueId()).isTptoogle()){
//
//                    sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));
//
//                    return false;
//                }
//
//                player2 = Bukkit.getPlayerExact(args[1]);
//                new TeleportUtil(player2.getLocation(), player1);
//                player1.sendMessage(Util.fixColor(Lang.INFO_TP_PLAYERTOPLAYER.replace("{player}", player1.getDisplayName()).replace("{target}", player2.getDisplayName())));
//            } else {
//                sender.sendMessage(Util.fixColor(Lang.ERROR_TP_NOTONLINE));
//            }
//        }
//        if(args.length == 3){
//            if(sender instanceof Player) {
//                Player player = (Player) sender;
//                double x;
//                double y;
//                double z;
//                try {
//                    x = Double.valueOf(args[0]);
//                    y = Double.valueOf(args[1]);
//                    z = Double.valueOf(args[2]);
//                    Location loc = new Location(player.getWorld(), x, y, z);
//                    player.getWorld().loadChunk((int)x, (int)y);
//                    new TeleportUtil(loc, player);
//                    String a = Lang.INFO_TP_CORDINATES.replace("{x}",String.valueOf(loc.getX()));
//                    a = a.replace("{y}",String.valueOf(loc.getY()));
//                    a = a.replace("{z}",String.valueOf(loc.getZ()));
//                    player.sendMessage(Util.fixColor(a));
//                } catch (Exception e) {
//                    sender.sendMessage(Util.fixColor(Lang.ERROR_TP_NOTCORDINATES));
//                }
//            } else {
//                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//            }
//        }
        return false;
    }
}
