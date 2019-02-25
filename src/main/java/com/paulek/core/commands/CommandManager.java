package com.paulek.core.commands;


import com.paulek.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CommandManager {

    private static final HashMap<String, Command> commands;
    private static SimplePluginManager menager = (SimplePluginManager) Core.getPlugin().getServer().getPluginManager();
    private static SimpleCommandMap map;


    public static void registerCommand(Command command){
        if(map == null){
            Field f = null;
            try {
                f = SimplePluginManager.class.getDeclaredField("commandMap");
            } catch (Exception e){
                e.printStackTrace();
            }
            f.setAccessible(true);
            try{
                map = (SimpleCommandMap) f.get(menager);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        map.register(command.getName(), command);
        commands.put(command.getName(), command);
    }


    static {
        commands = new HashMap<String, Command>();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e){
            e.printStackTrace();
        }
        f.setAccessible(true);
        try{
            map = (SimpleCommandMap) f.get(menager);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}