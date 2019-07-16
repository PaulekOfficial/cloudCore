package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class AnvilCMD extends Command {

    public AnvilCMD(Core core) {
        super("anvil", "opens a anvil", "/anvil", "core.cmd.anvil", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));

            player.sendMessage(ColorUtil.fixColor(Lang.INFO_ANVIL_OPENED));
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
