package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Msg extends TabCompleter {

    public Msg(Core core) {
        super("core.cmd.msg", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
