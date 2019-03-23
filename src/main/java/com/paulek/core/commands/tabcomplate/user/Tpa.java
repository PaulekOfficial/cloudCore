package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.commands.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Tpa extends TabCompleter {

    public Tpa(){
        super("core.cmd.tpa");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> playerList = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            playerList.add(player.getDisplayName());
        }
        return playerList;
    }
}
