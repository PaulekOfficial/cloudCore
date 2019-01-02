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

public class TpallCMD extends Command {

    public TpallCMD(){
        super("tpall", "teleports all player to yours location", "/tpall", "core.tpall", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

//        if(!(sender instanceof Player)){
//            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//        }
//
//        Location location = ((Player)sender).getLocation();
//
//        for(Player p : Bukkit.getOnlinePlayers()){
//
//            if(!UserStorage.getUser(p.getUniqueId()).isTptoogle()){
//                new TeleportUtil(location, p);
//            }
//
//        }
//
//        sender.sendMessage(Util.fixColor(Lang.INFO_TPALL_TELEPORTED));

        return false;
    }
}
