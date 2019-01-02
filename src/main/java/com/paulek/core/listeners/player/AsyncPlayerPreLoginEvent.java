package com.paulek.core.listeners.player;

import com.paulek.core.data.configs.Config;
import com.paulek.core.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncPlayerPreLoginEvent implements Listener {

    @EventHandler
    public void onPreLogin(org.bukkit.event.player.AsyncPlayerPreLoginEvent event){

        if(Config.ENABLE_WHITELIST){

            if(Config.SETTINGS_WHITELIST_ENABLED){

                if(!Config.SETTINGS_WHITELIST_LIST.contains(event.getName())){

                    event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Util.fixColor(Config.SETTINGS_WHITELIST_MOTD));

                }

            }

        }

    }

}
