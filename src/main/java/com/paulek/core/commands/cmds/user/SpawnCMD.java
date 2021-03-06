package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.PermissionsUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            spawnMethod(sender.getName(), "default", sender);

        } else if (args.length == 1) {

            spawnMethod(sender.getName(), args[0], sender);

        } else if(args.length == 2) {
            String spawnName = args[0];
            String playerName = args[1];

            return spawnMethod(playerName, spawnName, sender);

        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }

    private boolean spawnMethod(String playerName, String spawnName, CommandSender sender){

        boolean isPlayer = false;

        if(sender instanceof Player){
            Player a = Bukkit.getPlayer(playerName);
            if(a != null && playerName.equalsIgnoreCase("") && spawnName.equalsIgnoreCase("")){
                sender.sendMessage(ColorUtil.fixColor(getPermissionMessage()));
                return false;
            }
        }

        if(!PermissionsUtil.checkCommandPermission("spawn." + spawnName, sender)){
            sender.sendMessage(ColorUtil.fixColor(getPermissionMessage()));
            return false;
        }

        Vector3D location = getCore().getSpawnsStorage().get(spawnName);

        Player player = Bukkit.getPlayer(playerName);

        if(player == null){
            player = Bukkit.getPlayer(spawnName);
            if(player == null) {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SPAWN_PLAYEROFFINLE));
                return false;
            }
            isPlayer = true;
        }

        if(!isPlayer) {
            if (location == null) {
                if (!spawnName.equalsIgnoreCase("default")) {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SPAWN_NAMEDNOTSET.replace("{name}", spawnName)));
                    return true;
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SPAWN_NOTSET));
                    return true;
                }
            }
        } else {
            if(location == null){
                location = getCore().getSpawnsStorage().get("default");
                if(location == null){
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SPAWN_NOTSET));
                    return true;
                }
            }
        }

        if(isPlayer && !PermissionsUtil.checkCommandPermission("spawn.admin", sender)){
            sender.sendMessage(ColorUtil.fixColor(getPermissionMessage()));
            return false;
        }

        if(PermissionsUtil.checkCommandPermission("spawn.coolddownbypass", sender)){
            LocationUtil.safeTeleport(getCore().getConfiguration(), location.asLocation(), player);

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SPAWN_TELEPORT));
            return true;
        }

        final Location finalLocation = location.asLocation();
        final Player finalPlayer = player;

        BukkitTask id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
            @Override
            public void run(){
                LocationUtil.safeTeleport(getCore().getConfiguration(), finalLocation, finalPlayer);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SPAWN_TELEPORT));
            }
        }, getCore().getConfiguration().spawnDelay * 20);

        in_detly.put(((Player) sender).getUniqueId(), id.getTaskId());

        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SPAWN_DETY));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            List<String> allSpawns = new ArrayList<>(getCore().getSpawnsStorage().getAllSpawnNames());
            List<String> permittedSpawn = new ArrayList<>(allSpawns.size());
            for(String s : allSpawns) {
                if(PermissionsUtil.checkCommandPermission("spawn." + s, sender)){
                    permittedSpawn.add(s);
                }
            }
            return permittedSpawn;
        }

        if(args.length == 2) {
            if (PermissionsUtil.checkCommandPermission("spawn.admin", sender)) {
                List<String> playerList = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerList.add(player.getDisplayName());
                }
                return playerList;
            }
        }

        return new ArrayList<>();
    }

}
