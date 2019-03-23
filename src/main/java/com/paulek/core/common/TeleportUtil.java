package com.paulek.core.common;

import com.paulek.core.basic.data.Users;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportUtil {

    public TeleportUtil(Location location, Player player) {

        if(Config.TP_SAFELOCATION){

            player.teleport(safeLocation(location));

            return;
        }

        player.teleport(location);

    }

    public Location safeLocation(Location location) {

        float y_one = Bukkit.getWorld(location.getWorld().getName()).getHighestBlockYAt(location);

        Location to_fix = new Location(location.getWorld(), location.getX(), y_one, location.getZ(), location.getYaw(), location.getPitch());
        to_fix.setDirection(location.getDirection());

        float y_final = Bukkit.getWorld(to_fix.getWorld().getName()).getHighestBlockYAt(to_fix);

        Location fixed = new Location(to_fix.getWorld(), to_fix.getX(), y_final, to_fix.getZ(), to_fix.getYaw(), to_fix.getPitch());
        to_fix.setDirection(location.getDirection());

        return fixed;
    }

    public static boolean hasPlayerTpToogle(Player player){
        if(Users.getUser(player.getUniqueId()).getSettings().isTptoogle()){
            return true;
        }
        return false;
    }

    public boolean infoPlayerHasTpToogle(Player player, CommandSender sender){
        if(hasPlayerTpToogle(player)){

            sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));

            return true;
        }
        return false;
    }
}
