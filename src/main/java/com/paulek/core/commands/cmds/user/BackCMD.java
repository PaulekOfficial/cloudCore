package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
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

public class BackCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport = new HashMap<UUID, Integer>();

    public BackCMD() {
        super("back", "teleports to the last place of stay", "/back", "core.back", new String[]{});
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player)sender;

        final UUID uuid = player.getUniqueId();

        if(UserStorage.getUser(uuid).getLastlocation() != null){

            if(sender.hasPermission("core.wait.bypass")){
                Location location = UserStorage.getUser(uuid).getLastlocation();

                player.teleport(location);

                sender.sendMessage(Util.fixColor(Lang.INFO_BACK_TELEPORTED));
                return false;
            }

            sender.sendMessage(Util.fixColor(Lang.INFO_BACK_WAIT));

            BukkitTask id;

            id = Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                public void run() {
                    Location location = UserStorage.getUser(uuid).getLastlocation();

                    player.teleport(location);

                    sender.sendMessage(Util.fixColor(Lang.INFO_BACK_TPDONE));
                }
            }, Config.SETTINGS_BACK_DETLY * 20);

            to_teleport.put(player.getUniqueId(), id.getTaskId());

        } else {
            sender.sendMessage(Util.fixColor(Lang.ERROR_BACK_NOHISTORY));
            return false;
        }

        return false;
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport;
    }
}
