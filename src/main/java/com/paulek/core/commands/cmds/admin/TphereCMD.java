package com.paulek.core.commands.cmds.admin;

import com.paulek.core.basic.data.UserStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TphereCMD extends Command {

    public TphereCMD(){
        super("tphere", "teleport a player to you", "/tphere {player}", "core.tphere", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        if(args.length == 1){

            String nick = args[0];

            if(Bukkit.getPlayer(nick) != null){

                if(UserStorage.getUser(Bukkit.getPlayer(nick).getUniqueId()).getSettings().isTptoogle()){

                    sender.sendMessage(Util.fixColor(Lang.INFO_TPTOOGLE_TPDENY));

                    return false;
                }

                Location location = ((Player)sender).getLocation();

                Bukkit.getPlayer(nick).teleport(location);

                sender.sendMessage(Util.fixColor(Lang.INFO_TPHERE_TELEPORTED));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPHERE_PLAYEROFFINLE));
            }

        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }
}
