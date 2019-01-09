package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MsgspyCMD extends Command {

    public MsgspyCMD() {
        super("spymsg", "spy private messages", "/mspy {on, off}", "core.mspy", new String[] {"mspy", "socialspy"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            if (args[0].equalsIgnoreCase("on")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (!UserStorage.getUser(uuid).getSettings().isSocialspy()) {
                    UserStorage.getUser(uuid).getSettings().setSocialspy(true);
                    sender.sendMessage(Util.fixColor(Lang.INFO_MSG_SPY));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_ALREADY));
                }
            }
            if (args[0].equalsIgnoreCase("off")) {
                UUID uuid = ((Player) sender).getUniqueId();
                if (UserStorage.getUser(uuid).getSettings().isSocialspy()) {
                    UserStorage.getUser(uuid).getSettings().setSocialspy(false);
                    sender.sendMessage(Util.fixColor(Lang.INFO_MSG_DISABLE));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_ALREADYNO));
                }
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
