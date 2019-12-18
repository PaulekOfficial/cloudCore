package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchCMD extends Command {

    public WorkbenchCMD(Core core) {
        super("workbench", "opens a workbench", "/workbench", "core.cmd.workbench", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            player.openWorkbench(player.getLocation(), true);

            player.sendMessage(ColorUtil.fixColor(Lang.INFO_WORKBENCH_OPENED));
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
