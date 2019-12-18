package com.paulek.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CommandManager {

    private static final HashMap<String, Command> commands;
    private static CommandMap map;

    static {
        commands = new HashMap<>();
        Field f = null;
        try {
            f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert f != null;
        f.setAccessible(true);
        try {
            map = (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(false);
    }

    public void registerCommand(Command command) {
        map.register(command.getName(), command);
        commands.put(command.getName(), command);
    }
}