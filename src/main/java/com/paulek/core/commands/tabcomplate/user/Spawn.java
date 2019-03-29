package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Spawn extends TabCompleter {

    public Spawn(Core core) {
        super("core.cmd.spawn", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (sender.hasPermission("core.cmd.spawn.admin")) {
            List<String> playerList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getDisplayName());
            }
            return playerList;
        }

        return new ArrayList<>();
    }
}
