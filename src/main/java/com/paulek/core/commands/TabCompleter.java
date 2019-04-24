package com.paulek.core.commands;

import com.paulek.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public abstract class TabCompleter implements org.bukkit.command.TabCompleter {

    private String permisions;
    private Core core;

    public TabCompleter(String permisions, Core core) {
        this.permisions = permisions;
        this.core = Objects.requireNonNull(core, "Core");
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.hasPermission(permisions)) return null;

        return onTabComplete(commandSender, strings);
    }

    public Core getCore() {
        return core;
    }
}
