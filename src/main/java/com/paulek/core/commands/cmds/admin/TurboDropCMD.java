package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TurboDropCMD extends Command {

    public TurboDropCMD(Core core){
        super("turbodrop", "Runs turbodrop", "/turbodrop <duration> <message>", "core.cmd.turbodrop", new String[]{"td"}, Objects.requireNonNull(core, "Core"));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length < 1){
            sender.sendMessage(ColorUtil.fixColor(getUsage()));
            return true;
        }

        int duration = getTimeInSecounds(args[0]);

        if(duration == -1){
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_DROP_TURBO_BADTIMESYNTAX));
            return true;
        }

        StringBuilder message = new StringBuilder();
        message.append(args[1]);
        message.append(" ");

        if(args.length > 2){
            for(int i = 2; i < args.length; i++){
                message.append(args[i]);
                message.append(" ");
            }
        }

        getCore().getDrops().initTurbo(duration, message.toString(), args[0]);

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    private int getTimeInSecounds(String string){
        int value = -1;
        if(string.contains("d")){
            value = Integer.parseInt(string.replaceAll("d", "")) * 24 * 60 * 60;
        }
        if (string.contains("h")){
            value = Integer.parseInt(string.replaceAll("h", "")) * 60 * 60;
        }
        if(string.contains("m")){
            value = Integer.parseInt(string.replaceAll("m", "")) * 60;
        }
        if(string.contains("s")){
            value = Integer.parseInt(string.replaceAll("s", ""));
        }
        return value;
    }
}
