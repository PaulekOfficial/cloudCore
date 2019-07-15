package com.paulek.core.basic.gui.menu;

import com.paulek.core.basic.gui.GUIWindow;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiMenu {

    private GUIWindow guiWindow;
    private List<GuiMenuItem> items = new ArrayList<>();

    public GuiMenu(String name, int rows){
        guiWindow = new GUIWindow(name, rows);
    }

    public Inventory buildMenu(){
        return guiWindow.getInventory();
    }

}
