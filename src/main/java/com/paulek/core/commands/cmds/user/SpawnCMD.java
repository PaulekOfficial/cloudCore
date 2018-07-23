package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class SpawnCMD extends Command {

    private static HashMap<UUID, Integer> in_detly = new HashMap<UUID, Integer>();

    public SpawnCMD(){
        super("spawn", "teleports to spawn", "/spawn", "core.spawn", new String[] {"spawnpoint"});
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if(sender.hasPermission("core.detly.bypass")){
            Player player = (Player) sender;

            Location location = new Location(Bukkit.getWorld(Config.SETTINGS_SPAWN_WORLD), Config.SETTINGS_SPAWN_BLOCKX, Config.SETTINGS_SPAWN_BLOCKY, Config.SETTINGS_SPAWN_BLOCKZ);
            location.setYaw((float)Config.SETTINGS_SPAWN_YAW);

            player.teleport(location);

            sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_TELEPORT));
        } else {

            BukkitTask id;

            id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                public void run() {

                    Player player = (Player) sender;

                    Location location = new Location(Bukkit.getWorld(Config.SETTINGS_SPAWN_WORLD), Config.SETTINGS_SPAWN_BLOCKX, Config.SETTINGS_SPAWN_BLOCKY, Config.SETTINGS_SPAWN_BLOCKZ);
                    location.setYaw((float)Config.SETTINGS_SPAWN_YAW);

                    player.teleport(location);

                    sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_TELEPORT));
                }
            }, Config.SETTINGS_SPAWN_DETLY * 20);

            in_detly.put(((Player) sender).getUniqueId(), id.getTaskId());

            sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_DETY));

        }

        return false;
    }

    public static HashMap<UUID, Integer> getIn_detly() {
        return in_detly;
    }
}
