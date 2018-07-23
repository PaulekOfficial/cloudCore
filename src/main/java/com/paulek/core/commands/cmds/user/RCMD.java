package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RCMD extends Command {

    public RCMD() {
        super("r", "reply to a message", "/r {message}", "core.r", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length != 0){
            UUID uuid = ((Player)sender).getUniqueId();
            if(MsgCMD.getMessages().containsKey(uuid)) {
                String s = "";
                for (int i = 0; i != args.length; i++) {
                    s += args[i] + " ";
                }
                String message = Util.fixColor(Lang.INFO_MSG_FORMAT.replace("{message}", s));
                if(Bukkit.getPlayer(MsgCMD.getMessages().get(uuid)) != null) {
                    Bukkit.getPlayer(MsgCMD.getMessages().get(uuid)).sendMessage(message.replace("{from}", sender.getName()));
                    sender.sendMessage(message.replace("{from}", sender.getName()));
                    for(Player p : Bukkit.getOnlinePlayers()){
                        UUID u = p.getUniqueId();
                        if(UserStorage.getUser(u).isSocialspy()){
                            String m = Util.fixColor(Lang.INFO_MSG_SPYFORMAT.replace("{from}", sender.getName()).replace("{to}", Bukkit.getPlayer(MsgCMD.getMessages().get(uuid)).getDisplayName()).replace("{message}", s));
                            p.sendMessage(m);
                        }
                    }
                }  else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_NOTHINGTOANSWER));
                }
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_PLAYEROFF));
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }
}
