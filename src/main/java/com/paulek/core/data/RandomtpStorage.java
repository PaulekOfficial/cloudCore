package com.paulek.core.data;

import com.paulek.core.data.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RandomtpStorage {

    private static List<Location> buttons = new ArrayList<Location>();
    private static List<String> but = new ArrayList<String>();


    public static List getList(){
        return buttons;
    }

    public static void setList(List<Location> button){
        buttons = button;
    }
    public static void addToList(Location location){
        String tosave = location.getWorld().getName() + "#" + location.getBlockX() + "#" + location.getBlockY() + "#" +
                location.getBlockZ();
        but.add(tosave);
        buttons.add(location);
    }
    public static void removeFromList(Location location){
        String tosave = location.getWorld().getName() + "#" + location.getBlockX() + "#" + location.getBlockY() + "#" +
                location.getBlockZ();
        but.remove(tosave);
        buttons.remove(location);
    }
    public static List getStringLoc(){
        return but;
    }

    public static void loadButtons(){
        List<String> buttonstoconvert = Config.SETTINGS_RANDOMTELEPORT_BUTTONS;

        if(buttonstoconvert == null) return;

        for(String s : buttonstoconvert){
            if(s.equalsIgnoreCase("")) return;
            String[] str = s.split("#");
            Location loc = new Location(Bukkit.getWorld(str[0]), Integer.parseInt(str[1]),
                    Integer.parseInt(str[2]), Integer.parseInt(str[3]));
            buttons.add(loc);
        }

        if(buttons.isEmpty()){
            return;
        } else {
            for(Location loc : buttons){
                String tosave = loc.getWorld().getName() + "#" + loc.getBlockX() + "#" + loc.getBlockY() + "#" +
                        loc.getBlockZ();
                but.add(tosave);
            }
            int size = buttons.size();
        }
    }

}
