package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.commands.TabCompleter;
import com.paulek.core.common.TabCompleterUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Delhome extends TabCompleter {

    public Delhome(){
        super("core.cmd.delhome");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return TabCompleterUtils.getHomes(sender);
    }
}
