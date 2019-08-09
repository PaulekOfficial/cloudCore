package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.TabCompleterUtils;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelhomeCMD extends Command {

    public DelhomeCMD(Core core) {
        super("delhome", "delete a home", "/delhome {name}", "core.cmd.delhome", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            User user = getCore().getUsersStorage().getUser(player.getUniqueId());

            if (args.length == 0) {

                if (user.getHome("home") != null) {

                    user.removeHome("home");

                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_DELETED));

                } else {

                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_HOME_DELHOME));

                }

            } else if (args.length == 1) {

                if (user.getHome(args[0]) != null) {

                    user.removeHome(args[0]);

                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_HOME_DELETED));

                } else {

                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_HOME_DELNAMEDHOME));

                }

            } else {
                sender.sendMessage(getUsage());
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1){
            TabCompleterUtils.getHomes(sender, getCore());
        }
        return new ArrayList<>();
    }

}
