package com.paulek.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class TabCompleter implements org.bukkit.command.TabCompleter {

    private String permisions;

    public TabCompleter(String permisions){
        this.permisions = permisions;
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!commandSender.hasPermission(permisions)) return null;

        return onTabComplete(commandSender, strings);
    }
}
