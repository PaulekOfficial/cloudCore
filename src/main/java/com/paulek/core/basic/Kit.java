package com.paulek.core.basic;

import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.common.ItemUtil;
import com.paulek.core.common.TimeUtils;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Kit")
public class Kit implements ConfigurationSerializable {

    private String name;
    private String message;
    private boolean showInGui;
    private Material material;
    private ItemStack[] content;
    private String permission;
    private String timePeriod;

    public Kit(String name, String message, boolean showInGui, Material material, ItemStack[] content, String permission, String timePeriod) {
        this.name = name;
        this.message = message;
        this.showInGui = showInGui;
        this.material = material;
        this.content = content;
        this.permission = permission;
        this.timePeriod = timePeriod;
    }

    public static Kit deserialize(Map<String, Object> map) {

        String name = (String) map.get("name");
        String message = (String) map.get("message");
        boolean showInGui = (boolean) map.get("show-in-gui");
        Material material = Material.valueOf((String) map.get("material"));
        ItemStack[] content = ItemUtil.deserializeItemStackList((String[]) ((List<String>) map.get("items")).toArray());
        String permission = (String) map.get("permission");
        String timePeriod = (String) map.get("time-period");

        return new Kit(name, message, showInGui, material, content, permission, timePeriod);
    }

    //TODO serialize kit
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("name", name);
        map.put("message", message);
        map.put("show-in-gui", showInGui);
        map.put("material", material.name().toUpperCase());
        map.put("items", null);
        map.put("permission", permission);
        map.put("time-period", timePeriod);

        return map;
    }

    public boolean canAccess(Player player){
        return player.hasPermission(permission);
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShowInGui() {
        return showInGui;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack[] getContent() {
        return content;
    }

    public LocalDateTime getRenewTime(){
        return LocalDateTime.now().plusSeconds(TimeUtils.periodStringToLong(timePeriod));
    }

    public String getTimePeriod() {
        return timePeriod;
    }
}
