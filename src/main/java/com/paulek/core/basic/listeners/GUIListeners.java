package com.paulek.core.basic.listeners;

import com.paulek.core.basic.GUIItem;
import com.paulek.core.basic.GUIWindow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListeners implements Listener {

    @EventHandler
    public void onInvOpen(InventoryClickEvent event){
        GUIWindow gui = GUIWindow.getGuiWindowMap(event.getInventory().getName());
        if(gui != null){
            GUIItem item = gui.getItem(event.getSlot());
            if(item != null){
                item.invClick(event);
            }
            if(gui.isCancellOpen()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){
        GUIWindow gui = GUIWindow.getGuiWindowMap(event.getInventory().getName());
        if(gui != null){
            gui.callClose(event);
        }
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent event){
        GUIWindow gui = GUIWindow.getGuiWindowMap(event.getInventory().getName());
        if(gui != null){
            gui.callOpen(event);
        }
    }

    @EventHandler
    public void onInvInteract(InventoryInteractEvent event){
        GUIWindow gui = GUIWindow.getGuiWindowMap(event.getInventory().getName());
        if(gui != null){
            if(gui.isCancellInteract()) event.setCancelled(true);
        }
    }
}
