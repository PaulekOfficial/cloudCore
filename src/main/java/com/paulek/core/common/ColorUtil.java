package com.paulek.core.common;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    public static String fixColor(String string) {
        return ChatColor.translateAlternateColorCodes('$', string);
    }

    public static List<String> fixColors(List<String> string) {
        List<String> arrayList = new ArrayList<>();
        for (String line : string) {
            arrayList.add(ChatColor.translateAlternateColorCodes('$', line));
        }
        return arrayList;
    }

}
