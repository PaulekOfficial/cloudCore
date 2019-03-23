package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkbenchCMD extends Command {

    public WorkbenchCMD() {
        super("workbench", "opens a workbench", "/workbench", "core.cmd.workbench", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            player.openWorkbench(player.getLocation(), true);

            player.sendMessage(Util.fixColor(Lang.INFO_WORKBENCH_OPENED));
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
