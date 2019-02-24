package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCMD extends Command {

    public SpeedCMD(){
        super("speed", "change yours speed", "/speed {player} {speed}", "core.speed", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length >= 1){

            if(args.length == 1){

                if(sender instanceof Player) {

                    Player player = (Player)sender;

                    double a = 0;

                    try {

                        a = Double.valueOf(args[0]);

                    } catch (Exception e){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if(a > 10){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_VALUE));

                        return false;
                    }

                    a = a*0.1;

                    if (player.isFlying()){
                        player.setFlySpeed((float) a);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    } else {
                        player.setWalkSpeed((float) a);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    }

                } else {
                    sender.sendMessage(getUsage());
                }

            } else if (args.length == 2){

                String p = args[0];

                if (Bukkit.getPlayer(p) != null){

                    Player player = Bukkit.getPlayer(p);

                    double a = 0;

                    try {

                        a = Double.valueOf(args[1]);

                    } catch (Exception e){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if(a > 10){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_VALUE));

                        return false;
                    }

                    a = a*0.1;

                    if (player.isFlying()){
                        player.setFlySpeed((float) a);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    } else {
                        player.setWalkSpeed((float) a);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    }

                    sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_PLAYERCHANGED));

                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOPLAYER));
                }

            } else {
                sender.sendMessage(getUsage());
            }

        }

        return false;
    }
}
