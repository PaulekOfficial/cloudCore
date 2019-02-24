package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCMD extends Command {

    public TimeCMD(){
        super("time", "set time on world", "/time (time)", "core.command.time", new String[] {"czas"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(this.getUsage());
        }
        if(args.length == 1){
            if(sender instanceof Player){
                try{
                    long time = Long.valueOf(args[0]);
                    ((Player) sender).getWorld().setTime(time);
                    sender.sendMessage(Util.fixColor(Lang.INFO_TIME_SET.replace("{world}", ((Player) sender).getWorld().getName()).replace("{time}", String.valueOf(time))));
                } catch (Exception e){
                    sender.sendMessage(Util.fixColor(Lang.ERROR_THATSNOTANUMBER));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        if(args.length == 2){
            long time = 0;
            try{
                time = Long.valueOf(args[0]);
            } catch (Exception e){
                sender.sendMessage(Util.fixColor(Lang.ERROR_THATSNOTANUMBER));
                return false;
            }
            try{
                World world = Bukkit.getWorld(args[1]);
                world.setTime(time);
                sender.sendMessage(Util.fixColor(Lang.INFO_TIME_SET.replace("{world}", args[1]).replace("{time}", String.valueOf(time))));
            } catch (Exception e){
                sender.sendMessage(Util.fixColor(Lang.ERROR_TIME_NOWORLD));
            }
        }
        if(args.length > 2){
            sender.sendMessage(this.getUsage());
        }
        return false;
    }
}
