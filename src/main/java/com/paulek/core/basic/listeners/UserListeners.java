package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.User;
import com.paulek.core.common.MojangApiUtil;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class UserListeners implements Listener {

    private Core core;

    public UserListeners(Core core){
        this.core = Objects.requireNonNull(core, "Ccre");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        User user = core.getUsersStorage().getUser(event.getEntity().getUniqueId());
        user.setLastlocation(event.getEntity().getLocation());

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        core.getUsersStorage().checkPlayer(event.getPlayer());


        Bukkit.getScheduler().runTaskLaterAsynchronously(core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                UUID fakeUUID = event.getPlayer().getUniqueId();
                String nick = event.getPlayer().getDisplayName();
                if (!core.isOnlineMode() && Config.SKINS_ENABLE) {

                    if (core.getSkinsStorage().getSkin(fakeUUID) == null) {

                        Skin skin = MojangApiUtil.getPremiumSkin(nick, core);

                        if (skin == null){
                            Random random = new Random();

                            if(core.getSkinsStorage().getPlayerSkins().size() >= 60){
                                Object[] skinArray = core.getSkinsStorage().getPlayerSkins().values().toArray();
                                skin = (Skin) skinArray[random.nextInt(skinArray.length)];
                            } else {
                                while (true) {
                                    String randomPremiumNick = Config.SKINS_SKINSFORNONPREMIUM.get(random.nextInt(Config.SKINS_SKINSFORNONPREMIUM.size()));
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

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {

            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLogoutlocation(event.getPlayer().getLocation());
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastActivity(LocalDateTime.now());

        }

    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        if (core.getUsersStorage().getUser(event.getPlayer().getUniqueId()) != null) {
            core.getUsersStorage().getUser(event.getPlayer().getUniqueId()).setLastlocation(event.getFrom());
        }

    }

}
