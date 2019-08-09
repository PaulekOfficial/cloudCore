package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import common.MojangApiUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class SkinListeners implements Listener {

    private Core core;

    public SkinListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        core.getUsersStorage().checkPlayer(event.getPlayer());


        Bukkit.getScheduler().runTaskLaterAsynchronously(core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                UUID fakeUUID = event.getPlayer().getUniqueId();
                String nick = event.getPlayer().getDisplayName();
                if (!core.isOnlineMode() && core.getConfiguration().skinsEnabled) {

                    if (core.getSkinsStorage().getSkin(fakeUUID) == null) {

                        Skin skin = MojangApiUtil.getPremiumSkin(nick, core);

                        if (skin == null){
                            Random random = new Random();

                            if(core.getSkinsStorage().getPlayerSkins().size() >= core.getConfiguration().skinsOverrideValue && core.getConfiguration().skinsOverride){
                                Object[] skinArray = core.getSkinsStorage().getPlayerSkins().values().toArray();
                                skin = (Skin) skinArray[random.nextInt(skinArray.length)];
                            } else {
                                while (true) {
                                    String randomPremiumNick = core.getConfiguration().skinsList.get(random.nextInt(core.getConfiguration().skinsList.size()));
                                    skin = MojangApiUtil.getPremiumSkin(randomPremiumNick, core);
                                    if (skin != null) {
                                        break;
                                    }
                                }
                            }

                        }

                        skin.updateSkinForPlayer(Bukkit.getPlayer(fakeUUID));
                        skin.applySkinForPlayers(Bukkit.getPlayer(fakeUUID));
                        skin.setDirty(true);

                        core.getSkinsStorage().addSkin(fakeUUID, skin);

                        return;
                    }

                    Skin skin = core.getSkinsStorage().getSkin(fakeUUID);
                    if (skin.getLastUpdate().isAfter(LocalDateTime.now())) {
                        Skin newSkin = MojangApiUtil.getPremiumSkin(nick, core);
                        if (newSkin != null) {
                            skin = newSkin;
                            skin.setDirty(true);
                        }
                    }
                    skin.updateSkinForPlayer(Bukkit.getPlayer(fakeUUID));
                    skin.applySkinForPlayers(Bukkit.getPlayer(fakeUUID));
                }
            }
        }, 10);

    }

}
