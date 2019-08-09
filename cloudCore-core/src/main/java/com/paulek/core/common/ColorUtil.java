package com.paulek.core.common;

import com.paulek.core.common.io.Config;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    public static String fixColor(String string) {
        return ChatColor.translateAlternateColorCodes('$', string);
    }

    public static List<String> fixColors(String... strings) {
        List<String> arrayList = new ArrayList<>();
        for (String line : strings) {
            arrayList.add(ChatColor.translateAlternateColorCodes(Config.COLOR_MARK.toCharArray()[0], line));
        }
        return arrayList;
    }

}
