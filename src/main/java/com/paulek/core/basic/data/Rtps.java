package com.paulek.core.basic.data;

import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Rtps {

    private List<Location> buttons = new ArrayList<Location>();
    private List<String> but = new ArrayList<String>();


    public List getList() {
        return buttons;
    }

    public void addToList(Location location) {
        String tosave = location.getWorld().getName() + "#" + location.getBlockX() + "#" + location.getBlockY() + "#" +
                location.getBlockZ();
        but.add(tosave);
        buttons.add(location);
    }

    public void removeFromList(Location location) {
        String tosave = location.getWorld().getName() + "#" + location.getBlockX() + "#" + location.getBlockY() + "#" +
                location.getBlockZ();
        but.remove(tosave);
        buttons.remove(location);
    }

    public List getStringLoc() {
        return but;
    }

    public void loadButtons() {
        List<String> buttonstoconvert = Config.RTP_BUTTONLIST;

        if (buttonstoconvert == null) return;

        for (String s : buttonstoconvert) {
            if (s.equalsIgnoreCase("")) return;
            String[] str = s.split("#");
            Location loc = new Location(Bukkit.getWorld(str[0]), Integer.parseInt(str[1]),
                    Integer.parseInt(str[2]), Integer.parseInt(str[3]));
            buttons.add(loc);
        }

        if (buttons.isEmpty()) {
            return;
        } else {
            for (Location loc : buttons) {
                String tosave = loc.getWorld().getName() + "#" + loc.getBlockX() + "#" + loc.getBlockY() + "#" +
                        loc.getBlockZ();
                but.add(tosave);
            }
            int size = buttons.size();
        }
    }

}
