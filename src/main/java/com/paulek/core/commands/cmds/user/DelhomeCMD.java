package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.User;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeCMD extends Command {

    public DelhomeCMD(){
        super("delhome", "delete a home", "/delhome {name}", "core.delhome", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

//        if(sender instanceof Player) {
//
//            Player player = (Player) sender;
//            User user = UserStorage.getUser(player.getUniqueId());
//
//            if (args.length == 0) {
//
//                if(user.getHome().containsKey("default")){
//
//                    user.getHome().remove("default");
//
//                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DELETED));
//
//                } else {
//
//                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_DELHOME));
//
//                }
//
//            } else if (args.length == 1) {
//
//                if(user.getHome().containsKey(args[0])){
//
//                    user.getHome().remove(args[0]);
//
//                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DELETED));
//
//                } else {
//
//                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_DELNAMEDHOME));
//
//                }
//
//            } else {
//                sender.sendMessage(getUsage());
//            }
//        } else {
//            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//        }

        return false;
    }
}
