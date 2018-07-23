package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
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

                    int a;

                    try {

                        a = Integer.valueOf(args[0]);

                    } catch (Exception e){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if(a > 10){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_VALUE));

                        return false;
                    }

                    float f = 0.1F;

                    switch (a){
                        case 1: f = 0.1F;
                                break;
                        case 2: f = 0.2F;
                            break;
                        case 3: f = 0.3F;
                            break;
                        case 4: f = 0.4F;
                            break;
                        case 5: f = 0.5F;
                            break;
                        case 6: f = 0.6F;
                            break;
                        case 7: f = 0.7F;
                            break;
                        case 8: f = 0.8F;
                            break;
                        case 9: f = 0.9F;
                            break;
                        case 10: f = 1.0F;
                            break;
                    }

                    if (player.isFlying()){
                        player.setFlySpeed(f);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    } else {
                        player.setWalkSpeed(f);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    }

                } else {
                    sender.sendMessage(getUsage());
                }

            } else if (args.length == 2){

                String p = args[0];

                if (Bukkit.getPlayer(p) != null){

                    Player player = Bukkit.getPlayer(p);

                    int a;

                    try {

                        a = Integer.valueOf(args[1]);

                    } catch (Exception e){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if(a > 10){

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_VALUE));

                        return false;
                    }

                    float f = 0.1F;

                    switch (a){
                        case 1: f = 0.1F;
                            break;
                        case 2: f = 0.2F;
                            break;
                        case 3: f = 0.3F;
                            break;
                        case 4: f = 0.4F;
                            break;
                        case 5: f = 0.5F;
                            break;
                        case 6: f = 0.6F;
                            break;
                        case 7: f = 0.7F;
                            break;
                        case 8: f = 0.8F;
                            break;
                        case 9: f = 0.9F;
                            break;
                        case 10: f = 1.0F;
                            break;
                    }

                    if (player.isFlying()){
                        player.setFlySpeed(f);
                        sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
                    } else {
                        player.setWalkSpeed(f);
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
