package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCMD extends Command {

    public InvseeCMD(Core core) {
        super("invsee", "open player inventory", "/invsee {player}", "core.cmd.invsee", new String[]{"inv"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            if (args.length == 1) {

                String s = args[0];

                if (Bukkit.getPlayer(s) != null) {

                    Player player = Bukkit.getPlayer(s);

                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_INV_OPENED.replace("{player}", player.getDisplayName())));

                    ((Player) sender).openInventory(player.getInventory());

                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_INV_NOONLINE));
                    return false;
                }

            } else {
                sender.sendMessage(getUsage());
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
