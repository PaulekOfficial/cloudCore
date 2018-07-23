package com.paulek.core.listeners.player;

import com.paulek.core.data.UserStorage;
import com.paulek.core.data.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerGameModeChangeEvent implements Listener {

    @EventHandler
    public void onChange(org.bukkit.event.player.PlayerGameModeChangeEvent event){

        User user = UserStorage.getUser(event.getPlayer().getUniqueId());

        user.setGamemode(event.getNewGameMode().getValue());

    }
}
