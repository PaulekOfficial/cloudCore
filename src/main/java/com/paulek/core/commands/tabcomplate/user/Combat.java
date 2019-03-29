package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Combat extends TabCompleter {

    public Combat(Core core) {
        super("core.cmd.combat", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}