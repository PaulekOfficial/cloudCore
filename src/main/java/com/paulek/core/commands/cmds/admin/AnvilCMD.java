package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class AnvilCMD extends Command {

    public AnvilCMD() {
        super("anvil", "opens a anvil", "/anvil", "core.anvil", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));

            player.sendMessage(Util.fixColor(Lang.INFO_ANVIL_OPENED));
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
