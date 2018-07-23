package com.paulek.core.data.objects;

import org.bukkit.Material;

import java.util.List;

public class Drop {

    private String name;
    private Material item;
    private float chance;
    private String gui_name;
    private List<Material> tools;
    private String height;
    private boolean fortune;
    private int exp;
    private boolean can_disable;
    private String amount;
    private String message;
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getItem() {
        return item;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    public String getGui_name() {
        return gui_name;
    }

    public void setGui_name(String gui_name) {
        this.gui_name = gui_name;
    }

    public List<Material> getTools() {
        return tools;
    }

    public void setTools(List<Material> tools) {
        this.tools = tools;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public boolean isFortune() {
        return fortune;
    }

    public void setFortune(boolean fortune) {
        this.fortune = fortune;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isCan_disable() {
        return can_disable;
    }

    public void setCan_disable(boolean can_disable) {
        this.can_disable = can_disable;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
