package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MsgCMD extends Command {

    private static HashMap<UUID, String> messages = new HashMap<UUID, String>();

    public MsgCMD(){
        super("msg", "sends a private message", "/msg {player} {message}", "core.msg", new String[]{"wiadomosc"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            String tosend = args[0];
            if (Bukkit.getPlayer(tosend) != null) {
                UUID uuid = ((Player) sender).getUniqueId();
                if(!messages.containsKey(uuid)) {
                    messages.put(uuid, tosend);
                }
                Player send = Bukkit.getPlayer(tosend);
                UUID u = send.getUniqueId();
                if(!messages.containsKey(u)) {
                    messages.put(u, sender.getName());
                }
                String s = "";
                for (int i = 1; i != args.length; i++) {
                    s += args[i] + " ";
                }
                String message = Util.fixColor(Lang.INFO_MSG_FORMAT).replace("{message}", s);
                send.sendMessage(message.replace("{from}", sender.getName()));
                sender.sendMessage(message.replace("{from}", sender.getName()));
                for(Player p : Bukkit.getOnlinePlayers()){
                    UUID ue = p.getUniqueId();
                    if(UserStorage.getUser(ue).isSocialspy()){
                        String m = Util.fixColor(Lang.INFO_MSG_SPYFORMAT.replace("{from}", sender.getName()).replace("{to}", send.getDisplayName()).replace("{message}", s));
                        p.sendMessage(m);
                    }
                }
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_MSG_PLAYEROFF));
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }

    public static HashMap<UUID, String> getMessages() {
        return messages;
    }

}
