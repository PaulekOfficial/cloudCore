package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.TeleportUtil;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCMD extends Command {

    public WorldCMD(){
        super("world", "teleport to another world", "/world {name}", "core.world", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 1) {

            if(!(sender instanceof Player)){
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
                return false;
            }

            World world = null;

            if (args[0].equalsIgnoreCase("nether")) {

                for (World w : Bukkit.getWorlds()) {

                    if (w.getEnvironment().equals(World.Environment.NETHER)) {

                        world = w;

                    }

                }

            } else if (args[0].equalsIgnoreCase("normal")) {

                for (World w : Bukkit.getWorlds()) {

                    if (w.getEnvironment().equals(World.Environment.NORMAL)) {

                        world = w;

                    }

                }

            } else {

                try {

                    int id = Integer.valueOf(args[0]);

                    if (Bukkit.getWorlds().get(id) != null) {

                        world = Bukkit.getWorlds().get(id);

                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
                        return false;
                    }

                } catch (Exception e) {

                    if (Bukkit.getWorld(args[0]) != null) {

                        world = Bukkit.getWorld(args[0]);

                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
                        return false;
                    }

                }

            }

            if (world != null) {

                Location location = world.getSpawnLocation();

                new TeleportUtil(location, (Player)sender);

                sender.sendMessage(Util.fixColor(Lang.INFO_WORLD_TELEPORTED));

                return false;
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
            }

        } else if(args.length == 2){

            Player player = null;

            if(Bukkit.getPlayer(args[1]) != null){

                player = Bukkit.getPlayer(args[1]);

            } else {

                sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_PLAYEROFFINLE));

                return false;
            }

            World world = null;

            if (args[0].equalsIgnoreCase("nether")) {

                for (World w : Bukkit.getWorlds()) {

                    if (w.getEnvironment().equals(World.Environment.NETHER)) {

                        world = w;

                    }

                }

            } else if (args[0].equalsIgnoreCase("normal")) {

                for (World w : Bukkit.getWorlds()) {

                    if (w.getEnvironment().equals(World.Environment.NORMAL)) {

                        world = w;

                    }

                }

            } else {

                try {

                    int id = Integer.valueOf(args[0]);

                    if (Bukkit.getWorlds().get(id) != null) {

                        world = Bukkit.getWorlds().get(id);

                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
                        return false;
                    }

                } catch (Exception e) {

                    if (Bukkit.getWorld(args[0]) != null) {

                        world = Bukkit.getWorld(args[0]);

                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
                        return false;
                    }

                }

            }

            if (world != null) {

                Location location = world.getSpawnLocation();

                new TeleportUtil(location, player);

                player.sendMessage(Util.fixColor(Lang.INFO_WORLD_TELEPORTED));
                sender.sendMessage(Util.fixColor(Lang.INFO_WORLD_PLAYERTELEPORTED));

                return false;
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_WORLD_NOWORLD));
            }

        } else {
            sender.sendMessage(getUsage());
        }


        return false;
    }
}
