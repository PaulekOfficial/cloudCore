package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.User;
import com.paulek.core.utils.TeleportUtil;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class HomeCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport = new HashMap<UUID, Integer>();

    public HomeCMD(){
        super("home", "teleports to home", "/home {name}", "core.home", new String[]{});
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {

        if(sender instanceof Player){

            final Player player = (Player)sender;

            final User user = UserStorage.getUser(player.getUniqueId());

            if(args.length == 1){

                if(user.getHome().containsKey(args[0])){

                    if(!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                            public void run() {
                                new TeleportUtil(user.getHome().get(args[0]), player);

                                sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, Config.SETTINGS_HOME_DETLY * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        new TeleportUtil(user.getHome().get(args[0]), player);

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                    }



                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_HOME_NOTEXIST.replace("{name}", args[0])));
                }

            } else if(args.length == 0){

                if(user.getHome().size() > 1){

                    String s = "";

                    for(String str : user.getHome().keySet()){

                        s += str + ", ";

                    }

                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_MOREHOMES.replace("{homes}", s)));
                    return false;

                }

                if(user.getHome().containsKey("default")){

                    if(!sender.hasPermission("core.detly.bypass")) {

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORTING));

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                            public void run() {
                                player.teleport(user.getHome().get("default"));

                                sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                            }
                        }, Config.SETTINGS_HOME_DETLY * 20);

                        to_teleport.put(player.getUniqueId(), id.getTaskId());

                    } else {
                        player.teleport(user.getHome().get("default"));

                        sender.sendMessage(Util.fixColor(Lang.INFO_HOME_TELEPORT));
                    }

                } else {
                    sender.sendMessage(Util.fixColor(Lang.INFO_HOME_DEFAULTNOTEXIST));
                }

            } else {
                sender.sendMessage(getUsage());
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport;
    }
}
