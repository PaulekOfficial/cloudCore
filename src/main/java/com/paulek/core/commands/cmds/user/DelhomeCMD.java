package com.paulek.core.commands.cmds.user;

import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Users;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeCMD extends Command {

    public DelhomeCMD(){
        super("delhome", "delete a home", "/delhome {name}", "core.cmd.delhome", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;
            User user = Users.getUser(player.getUniqueId());

            if (args.length == 0) {

                if(user.getHome("home") != null){

                    user.removeHome("home");

                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DELETED));

                } else {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_DELHOME));

                }

            } else if (args.length == 1) {

                if(user.getHome(args[0]) != null){

                    user.removeHome(args[0]);

                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DELETED));

                } else {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_DELNAMEDHOME));

                }

            } else {
                sender.sendMessage(getUsage());
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
