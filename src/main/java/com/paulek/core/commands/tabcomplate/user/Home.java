package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import com.paulek.core.common.TabCompleterUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Home extends TabCompleter {

    public Home(Core core) {
        super("core.cmd.home", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return TabCompleterUtils.getHomes(sender, getCore());
    }

}
