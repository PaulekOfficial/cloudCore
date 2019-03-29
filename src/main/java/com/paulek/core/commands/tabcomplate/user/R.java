package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class R extends TabCompleter {

    public R(Core core) {
        super("core.cmd.r", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
