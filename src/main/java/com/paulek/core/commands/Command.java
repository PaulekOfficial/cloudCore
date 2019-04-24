package com.paulek.core.commands;

import com.paulek.core.Core;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Objects;

public abstract class Command extends org.bukkit.command.Command {

    private final String usage;
    private final String permisions;
    private final String description;
    private final String name;
    private Core core;


    public Command(final String name, final String description, final String usage, final String permisions, final String[] aliases, Core core) {
        super(name, description, usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.permisions = permisions;
        this.description = description;
        this.core = Objects.requireNonNull(core, "Core");
    }

    public abstract boolean execute(final CommandSender sender, final String args[]);

    public boolean execute(CommandSender sender, String label, String args[]) {
        if ((this.getPermisions() != null || this.getPermisions().equals("")) && (!sender.hasPermission(permisions))) {
            String error = "$3$lCORE $c-> $cYou dont have permisions ($4{perm}$c) to this!";
            error = error.replace("{perm}", this.getPermisions());
            error = Util.fixColor(error);
            sender.sendMessage(error);
            return false;
        }
        return execute(sender, args);
    }

    @Override
    public String getUsage() {
        return Util.fixColor(Lang.USAGE.replace("{usage}", usage));
    }

    public String getPermisions() {
        return permisions;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    public Core getCore() {
        return core;
    }
}