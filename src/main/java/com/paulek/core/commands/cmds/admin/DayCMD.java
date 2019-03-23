package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCMD extends Command {

    public DayCMD(){
        super("day", "makes day", "/day", "core.cmd.day", new String[] {"dzien"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            try{
                long time = 0;
                ((Player) sender).getWorld().setTime(time);
                sender.sendMessage(Util.fixColor(Lang.INFO_TIME_SET.replace("{world}", ((Player) sender).getWorld().getName()).replace("{time}", String.valueOf(time))));
            } catch (Exception e){
                sender.sendMessage(Util.fixColor(Lang.ERROR_THATSNOTANUMBER));
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }
        return false;
    }
}
