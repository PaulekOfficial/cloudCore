package com.paulek.core.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GUIWindow {

    private static Map<String, GUIWindow> guiWindowMap = new HashMap<>();

    private Map<Integer, GUIItem> gui;
    private int rows = 0;
    private String name;
    private Inventory inventory;
    private GUIEvent<InventoryOpenEvent> inventoryOpenEvent;
    private GUIEvent<InventoryCloseEvent> inventoryCloseEvent;
    private boolean registered;
    private boolean cancellInteract;
    private boolean cancellOpen;

    public GUIWindow(String name, int rows){

        this.name = name;
        inventory = Bukkit.createInventory(null, rows * 9, name);
        this. rows = rows;
        this.gui = new HashMap<>(rows * 9);
        this.cancellInteract = true;
        this.cancellOpen = true;

    }

    public boolean isCancellInteract() {
        return cancellInteract;
    }

    public void callOpen(InventoryOpenEvent event){
        inventoryOpenEvent.event(event);
    }

    public void callClose(InventoryCloseEvent event){
        inventoryCloseEvent.event(event);
    }

    public void setCancellInteract(boolean cancellInteract) {
        this.cancellInteract = cancellInteract;
    }

    public boolean isCancellOpen() {
        return cancellOpen;
    }

    public void setCancellOpen(boolean cancellOpen) {
        this.cancellOpen = cancellOpen;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void openInventory(Player player){
        if(registered) player.openInventory(inventory);
    }

    public void register(){
        registered = true;
        guiWindowMap.put(name, this);
    }

    private void unregister(){
        registered = false;
        guiWindowMap.remove(name);
    }

    public void setItem(int slot, GUIItem item){
        gui.put(slot, item);
        inventory.setItem(slot, item.getItemStack());
    }

    public GUIItem getItem(int slot){
        return gui.get(slot);
    }

    public void setInventoryOpenEvent(InventoryOpenEvent event){
        if(inventoryOpenEvent != null) this.inventoryOpenEvent.event(event);
    }

    public void setInventoryCloseEvent(InventoryCloseEvent event){
        if(inventoryCloseEvent != null) this.inventoryCloseEvent.event(event);
    }


    public static GUIWindow getGuiWindowMap(String name) {
        return guiWindowMap.get(name);
    }
}
