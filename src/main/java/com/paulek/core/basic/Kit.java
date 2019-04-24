package com.paulek.core.basic;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {

    private ItemStack guiItem;
    private String name;
    private String permission;
    private boolean enabled;
    private boolean showInGui;
    private int cooldown;
    private List<ItemStack> items;
    private String description;


    public Kit(String name, String permission, ItemStack guiItem, List<ItemStack> items, boolean enabled, boolean showInGui, int cooldown, String description) {
        this.name = name;
        this.permission = permission;
        this.guiItem = guiItem;
        this.items = items;
        this.enabled = enabled;
        this.showInGui = showInGui;
        this.cooldown = cooldown;
        this.description = description;
    }

    public ItemStack getGuiItem() {
        return guiItem;
    }

    public void setGuiItem(ItemStack guiItem) {
        this.guiItem = guiItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isShowInGui() {
        return showInGui;
    }

    public void setShowInGui(boolean showInGui) {
        this.showInGui = showInGui;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
}
