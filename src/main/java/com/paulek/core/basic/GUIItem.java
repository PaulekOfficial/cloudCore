package com.paulek.core.basic;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIItem {

    private ItemStack itemStack;
    private GUIEvent<InventoryClickEvent> inventoryClickEvent;

    public GUIItem(ItemStack itemStack, GUIEvent<InventoryClickEvent> inventoryClickEvent){
        this.inventoryClickEvent = inventoryClickEvent;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void invClick(InventoryClickEvent e) {
        this.inventoryClickEvent.event(e);
    }
}
