package com.paulek.core.listeners.player;

import com.paulek.core.Core;
import com.paulek.core.commands.cmds.admin.VanishCMD;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.User;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(final org.bukkit.event.player.PlayerJoinEvent event) {

        Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), new Runnable() {
            @Override
            public void run() {

                User user;

                if(UserStorage.getUser(event.getPlayer().getUniqueId()) == null) {

                    user = new User(event.getPlayer());

                    //user.setLastLogin(System.currentTimeMillis());

                    UserStorage.getUsers().put(user.getUuid(), user);

                } else {
                    user = UserStorage.getUser(event.getPlayer().getUniqueId());
                }

                if(Config.SETTINGS_SAVE_VANISH){

                    if(user.isVanish()){

                        BukkitTask id;

                        id = Bukkit.getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
                            public void run() {
                                for(Player player : Bukkit.getOnlinePlayers()){
                                    player.hidePlayer(event.getPlayer());
                                    Util.sendActionbar(event.getPlayer(), Util.fixColor(Lang.INFO_VANISH_ACTIONBAR));
                                }
                            }
                        },20, 20);

                        VanishCMD.getList().put(user.getUuid(), id);
                        event.getPlayer().sendMessage(Util.fixColor(Lang.INFO_VANISH_VANISHED));

                        for(Player player : Bukkit.getOnlinePlayers()){

                            if(player.hasPermission("core.vanish.info")){
                                player.sendMessage(Util.fixColor(Lang.INFO_VANISH_INFOJOIN.replace("{player}", event.getPlayer().getDisplayName())));
                            }

                        }

                    }

                }
            }
        });

    }
}
