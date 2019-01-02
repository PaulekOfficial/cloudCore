package com.paulek.core.listeners.player;

import com.paulek.core.commands.cmds.user.BackCMD;
import com.paulek.core.commands.cmds.user.HomeCMD;
import com.paulek.core.commands.cmds.user.SpawnCMD;
import com.paulek.core.commands.cmds.user.TpacceptCMD;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent event){

        UUID uuid = event.getPlayer().getUniqueId();

        if(SpawnCMD.getIn_detly().containsKey(uuid)) {

            if ((event.getFrom().getBlockX() < event.getTo().getBlockX()) || (event.getFrom().getBlockZ() < event.getTo().getBlockZ()) || (event.getFrom().getBlockY() < event.getTo().getBlockY())) {

                int id = SpawnCMD.getIn_detly().get(uuid);

                Bukkit.getScheduler().cancelTask(id);

                SpawnCMD.getIn_detly().remove(uuid);

                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_SPAWN_ABORT));

            }
        }

        if(BackCMD.getTo_teleport().containsKey(uuid)){
            if ((event.getFrom().getBlockX() < event.getTo().getBlockX()) || (event.getFrom().getBlockZ() < event.getTo().getBlockZ()) || (event.getFrom().getBlockY() < event.getTo().getBlockY())) {
                int id = BackCMD.getTo_teleport().get(uuid);

                Bukkit.getScheduler().cancelTask(id);

                BackCMD.getTo_teleport().remove(uuid);

                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_BACK_ABORT));
            }
        }

        if(TpacceptCMD.getTo_teleport().containsKey(uuid)) {

            if ((event.getFrom().getBlockX() < event.getTo().getBlockX()) || (event.getFrom().getBlockZ() < event.getTo().getBlockZ()) || (event.getFrom().getBlockY() < event.getTo().getBlockY())) {

                int id = TpacceptCMD.getTo_teleport().get(uuid);

                Bukkit.getScheduler().cancelTask(id);

                TpacceptCMD.getTo_teleport().remove(uuid);

                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_TPA_ABORT));

            }
        }

        if(HomeCMD.getTo_teleport().containsKey(uuid)){
            if ((event.getFrom().getBlockX() < event.getTo().getBlockX()) || (event.getFrom().getBlockZ() < event.getTo().getBlockZ()) || (event.getFrom().getBlockY() < event.getTo().getBlockY())) {

                int id = HomeCMD.getTo_teleport().get(uuid);

                Bukkit.getScheduler().cancelTask(id);

                HomeCMD.getTo_teleport().remove(uuid);

                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_HOME_ABORT));

            }
        }

    }
}
