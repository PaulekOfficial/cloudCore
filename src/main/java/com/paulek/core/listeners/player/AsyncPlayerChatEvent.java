package com.paulek.core.listeners.player;

import com.paulek.core.Core;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.managers.ChatManager;
import com.paulek.core.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AsyncPlayerChatEvent implements Listener {

    List<String> cenzored = Config.BLACKLISTED_WORDS;
    String replacecenzor = Config.CHAT_CENZOREDREPLACE;
    HashMap<UUID, Long> slowdon = new HashMap<UUID, Long>();
    private static HashMap<String, String> groups_format = new HashMap<String, String>();


    @EventHandler
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event){

        for(String s : groups_format.keySet()){
            if(Core.getPermission().playerInGroup(event.getPlayer(), s)){
                String format;
                if(Core.getChat().getPrimaryGroup(event.getPlayer()) != null) {
                    if (!event.getPlayer().hasPermission("core.chat.color")) {
                        format = Util.fixColor(groups_format.get(s).replace("{displayname}", Core.getChat().getGroupPrefix(event.getPlayer().getWorld(), Core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName())).replace("{message}", event.getMessage());
                    } else {
                        format = Util.fixColor(groups_format.get(s).replace("{displayname}", Core.getChat().getGroupPrefix(event.getPlayer().getWorld(), Core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName()).replace("{message}", event.getMessage()));
                    }
                    event.setFormat(format);
                }
            }
        }

        if(!ChatManager.isChatEnabled()){
            if(!event.getPlayer().hasPermission("core.chat.writewhendisabled")){
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }
        if(!Config.ENABLE_CHAT){
            if(!event.getPlayer().hasPermission("core.chat.disable")){
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }

        if(Config.ENABLE_NOUPPERCASE){
            if(!event.getPlayer().hasPermission("core.chat.nouppercase")){
                event.setMessage(event.getMessage().toLowerCase());
            }
        }

        if(Config.ENABLE_SLOWDOWN) {
            if(!event.getPlayer().hasPermission("core.chat.noslowdown")){
                UUID uuid = event.getPlayer().getUniqueId();
                if (slowdon.containsKey(uuid)) {
                    if (((System.currentTimeMillis() - slowdon.get(uuid)) / 1000) < Config.CHAT_SLOWDOWN) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_SLOWDOWN));
                    } else {
                        slowdon.put(uuid, System.currentTimeMillis());
                    }
                } else {
                    slowdon.put(uuid, System.currentTimeMillis());
                }
            }
        }
        if(Config.ENABLE_CENZOR){
            if(!event.getPlayer().hasPermission("core.chat.nocenzor")){
                for (String s : cenzored){
                    event.setMessage(event.getMessage().replaceAll(s, replacecenzor));
                }
            }
        }
    }

    public static void loadGroups(){

        for(String s : Config.CHAT_FORMATING){

            String[] a = s.split(" ");

            groups_format.put(a[0], s.replaceAll(a[0], "").replaceFirst(" ", ""));

        }

    }

}
