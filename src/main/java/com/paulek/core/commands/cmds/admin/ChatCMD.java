package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.managers.ChatManager;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCMD extends Command{

    public ChatCMD(){
        super("chat", "chat manipulation", "/chat (on,off,clear)", "core.cmd.chat", new String[] {"chatmanipulation", "czat"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("off")){
                if(!ChatManager.isChatEnabled()){
                    sender.sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLED));
                    return false;
                }
                ChatManager.setChatEnabled(false);
                sender.sendMessage(Util.fixColor(Lang.INFO_CHAT_SETOFF));
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_BRODCASTOFF));
            }
            if(args[0].equalsIgnoreCase("on")){
                if(ChatManager.isChatEnabled()){
                    sender.sendMessage(Util.fixColor(Lang.ERROR_CHAT_ENABLED));
                    return false;
                }
                ChatManager.setChatEnabled(true);
                sender.sendMessage(Util.fixColor(Lang.INFO_CHAT_SETON));
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_BRODCASTON));
            }
            if(args[0].equalsIgnoreCase("clear")){
                for(int i =0; i < 100; i++) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(" ");
                    }
                }
                Bukkit.broadcastMessage(Util.fixColor(Lang.INFO_CHAT_CLEAR.replace("{player}", sender.getName())));
            }
        } else {
            sender.sendMessage(this.getUsage());
        }
        return false;
    }
}
