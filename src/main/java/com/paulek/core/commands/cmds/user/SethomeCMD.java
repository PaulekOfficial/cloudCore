package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.User;
import com.paulek.core.utils.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SethomeCMD extends Command {

    private static HashMap<String, Integer> groups_amount = new HashMap<String, Integer>();

    public SethomeCMD(){
        super("sethome", "sets a home", "/sethome {name}", "core.sethome", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            if(args.length == 1){

                Player player = (Player) sender;

                int amount = 1;

                for(String s : groups_amount.keySet()){
                    if(Core.getPermission().playerInGroup(player, s)){
                        amount = groups_amount.get(s);
                    }
                }

                Location location = player.getLocation();

                User user = UserStorage.getUser(player.getUniqueId());

                if(!sender.hasPermission("core.bypass.home")) {
                    if (user.getHome().size() >= amount) {

                        sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_CANNOTSET));

                        return false;
                    }
                }

                if(!user.getHome().containsKey(args[0])){
                    user.getHome().put(args[0], location);
                    user.saveUser();
                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_SET.replace("{name}", args[0])));
                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_EXIST));
                }

            } else if (args.length == 0){

                Player player = (Player) sender;

                Location location = player.getLocation();

                User user = UserStorage.getUser(player.getUniqueId());

                user.getHome().put("default", location);

                sender.sendMessage(Util.fixColor(Lang.INFO_HOME_SETNONAME));
            } else {
                sender.sendMessage(getUsage());
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    public static void loadGroups(){

        for(String s : Config.HOME_AMOUNT){

            String[] a = s.split(" ");

            groups_amount.put(a[0], Integer.valueOf(s.replaceAll(a[0], "")));

        }

    }

}
