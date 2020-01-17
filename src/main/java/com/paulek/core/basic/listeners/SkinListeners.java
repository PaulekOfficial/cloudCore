package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.skin.SkinBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class SkinListeners implements Listener {

    private Core core;

    public SkinListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!core.getConfiguration().skinsEnabled) {
            return;
        }
        Player player = event.getPlayer();
        SkinBase skin = core.getSkinsStorage().get(player.getUniqueId());
        skin.applySkinForPlayers(player);
        skin.updateSkinForPlayer(player);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!core.getConfiguration().skinsEnabled) {
            return;
        }
        core.getSkinsStorage().delete(event.getPlayer().getUniqueId());
    }

}
