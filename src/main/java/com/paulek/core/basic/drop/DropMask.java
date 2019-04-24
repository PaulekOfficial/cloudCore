package com.paulek.core.basic.drop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DropMask {

    public abstract void breakBlock(Player player, ItemStack tool, Object object);
}
