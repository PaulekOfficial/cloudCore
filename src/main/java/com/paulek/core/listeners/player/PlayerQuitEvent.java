package com.paulek.core.listeners.player;

import com.paulek.core.commands.cmds.admin.VanishCMD;
import com.paulek.core.commands.cmds.user.SpawnCMD;
import com.paulek.core.commands.cmds.user.TpaCMD;
import com.paulek.core.commands.cmds.user.TpacceptCMD;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.data.objects.User;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event){

        UUID uuid = event.getPlayer().getUniqueId();

        if(VanishCMD.getList().containsKey(uuid)){

            BukkitTask id = VanishCMD.getList().get(uuid);

            Bukkit.getScheduler().cancelTask(id.getTaskId());

            VanishCMD.getList().remove(uuid);

        }

//        if(UserStorage.getUsers().containsKey(uuid)){
//
//            switch (User.STARAGE_TYPE){
//                case "json":
//                    UserStorage.getUsers().get(uuid).saveUser();
//                    UserStorage.getUsers().get(uuid).saveFile();
//                    break;
//                case "mysql":
//                    UserStorage.getUser(uuid).updateUserInDatabase();
//                    break;
//            }
//
//            UserStorage.getUsers().remove(uuid);
//        }

        if(TpacceptCMD.getTo_teleport().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(TpacceptCMD.getTo_teleport().get(uuid));
            TpacceptCMD.getTo_teleport().remove(uuid);
        }

        if(TpaCMD.getWaiting_to_accept().containsKey(uuid)){
            TpaCMD.getWaiting_to_accept().remove(uuid);
        }

        if(TpaCMD.getWaiting_to_accept_id().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(TpaCMD.getWaiting_to_accept_id().get(uuid));
            TpaCMD.getWaiting_to_accept_id().remove(uuid);
        }

        if(SpawnCMD.getIn_detly().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(SpawnCMD.getIn_detly().get(uuid));
            SpawnCMD.getIn_detly().remove(uuid);
        }

        if(CombatStorage.isMarked(uuid)){
            CombatStorage.unmark(uuid);

            if(Config.SETTINGS_COMBAT_BRODCASTLOGOUT) {

                String s = Util.fixColor(Lang.INFO_COMBAT_BRODCASTLOGOUT).replace("{player}", event.getPlayer().getName()).replace("{health}", Double.toString((int)event.getPlayer().getHealth()) + "♥");

                Bukkit.broadcastMessage(s);

            }

            if(Config.SETTINGS_COMBAT_KILLONLOGOUT) event.getPlayer().setHealth(0.0);

        }
    }
}
