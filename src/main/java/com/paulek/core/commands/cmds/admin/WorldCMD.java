package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldCMD extends Command {

    public WorldCMD(Core core) {
        super("world", "teleport to another world", "/world {name}", "core.cmd.world", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }

            World world = getWorld(args[0]);

            if (world != null) {

                Location location = world.getSpawnLocation();

                LocationUtil.safeTeleport(getCore().getConfiguration(), location, (Player) sender);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WORLD_TELEPORTED));

                return false;
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_WORLD_NOWORLD));
            }

        } else if (args.length == 2) {

            Player player = null;

            if (Bukkit.getPlayer(args[1]) != null) {

                player = Bukkit.getPlayer(args[1]);

            } else {

                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_WORLD_PLAYEROFFINLE));

                return false;
            }

            World world = getWorld(args[0]);

            if (world != null) {

                Location location = world.getSpawnLocation();

                LocationUtil.safeTeleport(getCore().getConfiguration(), location, player);

                player.sendMessage(ColorUtil.fixColor(Lang.INFO_WORLD_TELEPORTED));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_WORLD_PLAYERTELEPORTED));

                return false;
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_WORLD_NOWORLD));
            }

        } else {
            sender.sendMessage(getUsage());
        }


        return false;
    }

    private World getWorld(String name) {
        World world = null;
        if (name.equalsIgnoreCase("nether")) {

            for (World w : Bukkit.getWorlds()) {

                if (w.getEnvironment().equals(World.Environment.NETHER)) {

                    world = w;

                }

            }

        } else if (name.equalsIgnoreCase("normal")) {

            for (World w : Bukkit.getWorlds()) {

                if (w.getEnvironment().equals(World.Environment.NORMAL)) {

                    world = w;

                }

            }

        } else {

            try {

                int id = Integer.valueOf(name);

                if (Bukkit.getWorlds().get(id) != null) {

                    world = Bukkit.getWorlds().get(id);

                }

            } catch (Exception e) {

                if (Bukkit.getWorld(name) != null) {

                    world = Bukkit.getWorld(name);

                }

            }

        }
        return world;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            List<String> worldNames = new ArrayList<>();
            for(World world : Bukkit.getWorlds()){
                worldNames.add(world.getName());
            }
            return worldNames;
        }

        return new ArrayList<>();
    }

}
