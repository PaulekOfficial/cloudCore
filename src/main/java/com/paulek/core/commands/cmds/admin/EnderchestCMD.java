package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCMD extends Command {

    public EnderchestCMD() {
        super("enderchest", "open a enderchest", "/enderchest {player}", "core.enderchest", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {
            if (args.length == 0) {

                Player player = (Player) sender;

                player.openInventory(player.getEnderChest());

            } else if (args.length == 1) {

                if(Bukkit.getPlayer(args[0]) != null){

                    Player player = Bukkit.getPlayer(args[0]);

                    ((Player)sender).openInventory(player.getEnderChest());

                    sender.sendMessage(Util.fixColor(Lang.INFO_INV_ENDERCHEST));

                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_INV_NOONLINE));
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
