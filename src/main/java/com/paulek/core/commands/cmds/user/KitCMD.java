package com.paulek.core.commands.cmds.user;

import com.paulek.core.basic.GUIItem;
import com.paulek.core.basic.GUIWindow;
import com.paulek.core.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCMD extends Command {

    public KitCMD(){
        super("kit", "Get a kit", "/kit (name)", "core.kit", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        GUIWindow gui = new GUIWindow("Test", 1);

        GUIItem item = new GUIItem(new ItemStack(Material.STONE, 1 ,(short) 0),  event -> {
            Bukkit.broadcastMessage("pierdol się!");
        });

        gui.setItem(0, item);

        gui.register();

        gui.openInventory((Player)sender);

        return false;
    }
}
