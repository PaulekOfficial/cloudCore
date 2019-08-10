package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ActionBarUtil;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.TitleUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrodcastCMD extends Command {

    public BrodcastCMD(Core core) {
        super("brodcast", "brodcast a message", "/bc {title, action, chat} {message}", "core.cmd.bc", new String[]{"brodcast"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("title")) {
                String s;
                int i;
                if (args.length > 1) {
                    s = "";
                    for (i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        TitleUtil.sendTitle(p, ColorUtil.fixColor(Lang.INFO_BRODCAST_TITLE), ColorUtil.fixColor(s), 5, 60, 5);
                    }
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("action")) {
                String s;
                int i;
                if (args.length > 1) {
                    s = "";
                    for (i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        ActionBarUtil.sendActionbar(p, ColorUtil.fixColor(s));
                    }
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("chat")) {
                String s = "";
                if (args.length > 1) {
                    for (int i = 1; i != args.length; i++) {
                        s = s + args[i] + " ";
                    }
                    Bukkit.broadcastMessage(ColorUtil.fixColor(Lang.INFO_BRODCAST_CHAT).replace("{message}", s));
                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }
            } else {
                sender.sendMessage(getUsage());
                return false;
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            return Arrays.asList("title", "action", "chat");
        }

        return new ArrayList<>();
    }

}
