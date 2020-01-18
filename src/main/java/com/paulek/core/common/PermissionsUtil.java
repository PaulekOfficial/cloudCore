package com.paulek.core.common;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionsUtil {

    public static boolean checkPermission(String string, Player player) {
        return player.hasPermission("cloudcore." + string);
    }

    public static boolean checkCommandPermission(String string, Player player) {
        return player.hasPermission("cloudcore.command." + string);
    }

    public static boolean checkPermission(String string, CommandSender commandSender) {
        return commandSender.hasPermission("cloudcore." + string);
    }

    public static boolean checkCommandPermission(String string, CommandSender commandSender) {
        return commandSender.hasPermission("cloudcore.command." + string);
    }

}
