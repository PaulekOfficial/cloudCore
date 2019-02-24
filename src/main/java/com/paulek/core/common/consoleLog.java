package com.paulek.core.common;

import com.paulek.core.Core;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class consoleLog {
    private static Plugin plugin = Core.getPlugin();

    public static void warning(String message){
        plugin.getLogger().warning(message);
    }

    public static void info(String message){
        plugin.getLogger().info(message);
    }

    public static void log(String message, Level level){
        plugin.getLogger().log(level, message);
    }
}
