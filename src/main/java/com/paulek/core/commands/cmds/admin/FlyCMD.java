package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCMD extends Command{

    public FlyCMD(){
        super("fly", "allow flight", "/fly (player)", "core.cmd.fly", new String[0]);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            if(sender instanceof Player){
                Player player = (Player)sender;
                setPlayersFlight(player);
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        if(args.length == 1){
            if(sender.hasPermission("core.command.fly.other")) {
                if (sender instanceof Player) {
                    Player player = null;
                    if (Bukkit.getPlayerExact(args[0]) != null) {
                        player = Bukkit.getPlayerExact(args[0]);
                        setPlayersFlight(player);
                        sender.sendMessage(Util.fixColor(Lang.INFO_FLY_SETONFOR.replace("{player}", player.getDisplayName())));
                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_FLY_NOTONLINE));
                    }
                } else {
                    sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                }
            } else {
                String error = "$3$lCORE $c-> $cYou dont have permisions ($4{perm}$c) to this!";
                error = error.replace("{perm}", "core.command.fly.other");
                error = Util.fixColor(error);
                sender.sendMessage(error);
            }
        }
        if(args.length > 1){
            sender.sendMessage(this.getUsage());
        }
        return false;
    }

    private void setPlayersFlight(Player player){
        if(player.getAllowFlight()){
            player.setAllowFlight(false);
            player.sendMessage(Util.fixColor(Lang.INFO_FLY_SETOFF));
        } else {
            player.setAllowFlight(true);
            player.sendMessage(Util.fixColor(Lang.INFO_FLY_SETON));
        }
    }
}
