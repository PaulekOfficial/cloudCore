package com.paulek.core.basic.drop;

import com.paulek.core.common.DropUtil;
import com.paulek.core.common.ItemUtil;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("StoneDrop")
public class StoneDrop implements ConfigurationSerializable {

    private String name;
    private String message;
    private boolean canDisable;
    private ItemStack material;
    private List<ItemStack> tools;
    private int exp;
    private String permission;
    private double chance;
    private boolean fortune;
    private String amount;
    private String height;

    public StoneDrop(String name, String message, boolean canDisable, ItemStack material, List<ItemStack> tools, int exp, String permission, double chance, boolean fortune, String amount, String height) {
        this.name = name;
        this.message = message;
        this.canDisable = canDisable;
        this.material = material;
        this.tools = tools;
        this.exp = exp;
        this.permission = permission;
        this.chance = chance;
        this.fortune = fortune;
        this.amount = amount;
        this.height = height;
    }

    public static StoneDrop deserialize(Map<String, Object> map) {

        String name = "none";
        String message = "none";
        boolean canDisable = true;
        ItemStack material = null;
        List<ItemStack> tools = new ArrayList<>();
        int exp = 0;
        String permission = "none";
        double chance = 0;
        boolean fortune = true;
        String amount = "1-2";
        String height = ">255";

        name = (String) map.get("name");
        message = (String) map.get("message");
        canDisable = (Boolean) map.get("canDisable");
        material = ItemUtil.itemStackFromString((String) map.get("material"));
        tools.addAll(ItemUtil.itemStacksFromList((List) map.get("tools")));
        exp = (Integer) map.get("exp");
        permission = (String) map.get("permission");
        chance = (Double) map.get("chance");
        fortune = (Boolean) map.get("fortune");
        amount = (String) map.get("amount");
        height = (String) map.get("height");

        return new StoneDrop(name, message, canDisable, material, tools, exp, permission, chance, fortune, amount, height);
    }

    public boolean canDrop(Player player) {
        return permission == null || player.hasPermission(permission);
    }

    public double getChance(Player player) {
        //if (Config.DROP_VIP_ENABLE && player.hasPermission("core.drop.vip")) return chance * Config.DROP_VIP_MULTIPLIER;
        return chance;
    }

    public boolean correctTool(ItemStack itemStack) {
        return tools.contains(itemStack);
    }

    public boolean correctHight(Double height) {
        if (this.height.contains(">=")) {
            String[] values = this.height.split(">=");

            return (Integer.parseInt((values[0].equalsIgnoreCase("")) ? "0" : values[0]) >= height && height >= Integer.parseInt(values[1]));
        }
        if (this.height.contains("<=")) {
            String[] values = this.height.split("<=");

            return (Integer.parseInt((values[0].equalsIgnoreCase("")) ? "0" : values[0]) <= height && height <= Integer.parseInt(values[1]));
        }
        if (this.height.contains("=")) {

            return Double.parseDouble(this.height.replace("=", "")) == (height);

        }
        if (this.height.contains(">")) {
            String[] values = this.height.split(">");

            return (Integer.parseInt((values[0].equalsIgnoreCase("")) ? "0" : values[0]) > height && height > Integer.parseInt(values[1]));

        }
        if (this.height.contains("<")) {
            String[] values = this.height.split("<");

            return  (Integer.parseInt((values[0].equalsIgnoreCase("")) ? "0" : values[0]) < height && height < Integer.parseInt(values[1]));
        }
        return false;
    }

    public int getRandomAmount() {
        String[] strings = amount.split("-");
        return DropUtil.getRandomInteger(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("name", name);
        map.put("message", message);
        map.put("canDisable", canDisable);
        map.put("material", ItemUtil.itemstackToString(material));
        map.put("tools", ItemUtil.itemstacksToList(tools));
        map.put("exp", exp);
        map.put("permission", permission);
        map.put("chance", chance);
        map.put("fortune", fortune);
        map.put("amount", amount);
        map.put("height", height);

        return map;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCanDisable() {
        return canDisable;
    }

    public void setCanDisable(boolean canDisable) {
        this.canDisable = canDisable;
    }

    public ItemStack getMaterial() {
        return material;
    }

    public void setMaterial(ItemStack material) {
        this.material = material;
    }

    public List<ItemStack> getTools() {
        return tools;
    }

    public void setTools(List<ItemStack> tools) {
        this.tools = tools;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public boolean isFortune() {
        return fortune;
    }

    public void setFortune(boolean fortune) {
        this.fortune = fortune;
    }
}
