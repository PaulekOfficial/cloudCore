package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.TeleportUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class SpawnCMD extends Command {

    private static HashMap<UUID, Integer> in_detly = new HashMap<UUID, Integer>();

    public SpawnCMD(Core core) {
        super("spawn", "teleports to spawn", "/spawn {player}", "core.cmd.spawn", new String[]{"spawnpoint"}, core);
    }

    public static HashMap<UUID, Integer> getIn_detly() {
        return in_detly;
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }

            if (sender.hasPermission("core.cmd.spawn.cooldownbypass")) {
                Player player = (Player) sender;

                teleportSpawn(player);

                sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_TELEPORT));

                return false;
            } else {

                BukkitTask id;

                id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                    public void run() {

                        Player player = (Player) sender;

                        teleportSpawn(player);

                        sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_TELEPORT));
                    }
                }, Config.SPAWN_DETLY * 20);

                in_detly.put(((Player) sender).getUniqueId(), id.getTaskId());

                sender.sendMessage(Util.fixColor(Lang.INFO_SPAWN_DETY));

                return false;
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("core.cmd.spawn.admin")) {
                if (Bukkit.getPlayer(args[0]) != null) {

                    Player player = Bukkit.getPlayer(args[0]);

                    teleportSpawn(player);

                    player.sendMessage(Util.fixColor(Lang.INFO_SPAWN_PLAYERTELEPORTED));

                    return false;
                } else {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_SPAWN_PLAYEROFFINLE));

                    return false;
                }
            } else {
                sender.sendMessage(Util.fixColor(getPermissionMessage()));
            }
        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }

    private void teleportSpawn(Player player) {
        Location location = new Location(Bukkit.getWorld(Config.SPAWN_WORLD), Config.SPAWN_BLOCK_X, Config.SPAWN_BLOCK_Y, Config.SPAWN_BLOCK_Z);
        location.setYaw((float) Config.SPAWN_YAW);

        new TeleportUtil(location, player);
    }
}
