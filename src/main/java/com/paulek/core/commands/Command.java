package com.paulek.core.commands;

import com.paulek.core.Core;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public abstract boolean execute(final CommandSender sender, final String[] args);

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if ((this.getPermisions() != null || this.getPermisions().equals("")) && (!sender.hasPermission(permisions))) {
            String error = "$3$lCORE $c-> $cYou dont have permisions ($4{perm}$c) to this!";
            error = error.replace("{perm}", this.getPermisions());
            error = ColorUtil.fixColor(error);
            sender.sendMessage(error);
            return false;
        }
        return execute(sender, args);
    }

    public abstract List<String> tabComplete(final CommandSender sender, final String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if ((this.getPermisions() != null || this.getPermisions().equals("")) && (!sender.hasPermission(permisions))) {
            return new ArrayList<>();
        }
        return tabComplete(sender, args);
    }

    @Override
    public String getUsage() {
        return ColorUtil.fixColor(Lang.USAGE.replace("{usage}", usage));
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

    public Core getCore(){
        return core;
    }

    public String tl(String message, Object... objects) {
        return com.paulek.core.common.io.I18n.tl(message, objects);
    }

}