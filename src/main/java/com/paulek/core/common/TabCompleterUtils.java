package com.paulek.core.common;

import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Users;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterUtils {

    public static List<String> getHomes(CommandSender sender){
        if(sender instanceof Player){
            List<String> homes = new ArrayList<>();
            User user = Users.getUser(((Player)sender).getUniqueId());
            for(String name : user.getHomes().keySet()){
                homes.add(name);
            }
            return homes;
        }

        return new ArrayList<>();
    }
}
