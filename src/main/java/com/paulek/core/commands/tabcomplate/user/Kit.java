package com.paulek.core.commands.tabcomplate.user;

import com.paulek.core.Core;
import com.paulek.core.commands.TabCompleter;
import com.paulek.core.common.io.Kits;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Kit extends TabCompleter {

    public Kit(Core core) {
        super("core.cmd.kit", core);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> kits = new ArrayList<>();

        for (String kit : getCore().getKits().getKits().keySet()) {

            if (sender.hasPermission(getCore().getKits().getKits().get(kit).getPermission())) {
                kits.add(kit);
            }

        }

        return kits;
    }
}
