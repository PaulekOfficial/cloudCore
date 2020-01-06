package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
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

public class BackCMD extends Command {

    private static HashMap<UUID, Integer> to_teleport = new HashMap<UUID, Integer>();

    public BackCMD(Core core) {
        super("back", "teleports to the last place of stay", "/back", "core.cmd.back", new String[]{}, core);
    }

    public static HashMap<UUID, Integer> getTo_teleport() {
        return to_teleport;
    }

    @Override
    public boolean execute(final CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        final Player player = (Player) sender;

        final UUID uuid = player.getUniqueId();

        if (getCore().getUsersStorage().get(uuid).getLastlocation() != null) {

            if (sender.hasPermission("core.wait.bypass")) {
                Location location = getCore().getUsersStorage().get(uuid).getLastlocation();

                player.teleport(location);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_BACK_TELEPORTED));
                return false;
            }

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_BACK_WAIT));

            BukkitTask id;

            id = Bukkit.getScheduler().runTaskLater(getCore().getPlugin(), new Runnable() {
                public void run() {
                    Location location = getCore().getUsersStorage().get(uuid).getLastlocation();

                    player.teleport(location);

                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_BACK_TPDONE));
                }
            }, getCore().getConfiguration().teleportDelay * 20);

            to_teleport.put(player.getUniqueId(), id.getTaskId());

        } else {
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_BACK_NOHISTORY));
            return false;
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
