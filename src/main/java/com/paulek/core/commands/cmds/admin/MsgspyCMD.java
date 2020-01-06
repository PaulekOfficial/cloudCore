package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MsgspyCMD extends Command {

    public MsgspyCMD(Core core) {
        super("spymsg", "spy private messages", "/mspy {on, off}", "core.cmd.mspy", new String[]{"mspy", "socialspy"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("on")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (!getCore().getUsersStorage().get(uuid).isSocialSpy()) {
                    getCore().getUsersStorage().get(uuid).setSocialSpy(true);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_MSG_SPY));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_ALREADY));
                }
            }
            if (args[0].equalsIgnoreCase("off")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (getCore().getUsersStorage().get(uuid).isSocialSpy()) {
                    getCore().getUsersStorage().get(uuid).setSocialSpy(false);
                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_MSG_DISABLE));
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_ALREADYNO));
                }
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            return Arrays.asList("on", "off");
        }

        return new ArrayList<>();
    }

}
