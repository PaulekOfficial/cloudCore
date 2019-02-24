package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCMD extends Command {

    public HelpopCMD(){
        super("helpop", "send a message to moderators", "/helpop (message)", "core.helpop", new String[] {"pomocy"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length < 1){
            sender.sendMessage(Util.fixColor(this.getUsage()));
            return false;
        }
        String message = "";
        for(int i = 0; i < args.length; i++){
            message += args[i] + " ";
        }
        for (Player p : Bukkit.getOnlinePlayers()){
            if(p.hasPermission("core.helpop.recive")){
                p.sendMessage(Util.fixColor(Lang.INFO_HELPOP_FORMAT.replace("{player}", sender.getName()).replace("{message}", message)));
            }
        }
        sender.sendMessage(Util.fixColor(Lang.INFO_HELPOP_SENDED));
        return false;
    }
}
