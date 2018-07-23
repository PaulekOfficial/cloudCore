package com.paulek.core.commands;

import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class Command extends org.bukkit.command.Command {

    private final String usage;
    private final String permisions;
    private final String description;
    private final String name;


    public Command(final String name, final String description, final String usage, final String permisions, final String[] aliases){
        super(name, description, usage, (List) Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.permisions = permisions;
        this.description = description;
    }

    public abstract boolean execute(final CommandSender sender, final String args[]);

    public boolean execute(CommandSender sender, String label, String args[]){
        if((this.getPermisions() != null || this.getPermisions().equals("")) && (!sender.hasPermission(permisions))){
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
}