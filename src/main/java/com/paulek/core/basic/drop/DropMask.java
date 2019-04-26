package com.paulek.core.basic.drop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DropMask {

    private boolean dropped = false;

    public abstract void breakBlock(Player player, ItemStack tool, Object object);


    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }
}
