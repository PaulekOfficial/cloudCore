package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.commands.TabCompleter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class R extends TabCompleter {

    public R(){
        super("core.cmd.r");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
