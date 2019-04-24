package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Tpa extends TabCompleter {

    public Tpa(Core core) {
        super("core.cmd.tpa", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> playerList = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerList.add(player.getDisplayName());
        }
        return playerList;
    }
}
