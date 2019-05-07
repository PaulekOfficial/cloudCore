package com.paulek.core.basic.gui.menu;

import com.paulek.core.basic.gui.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class GuiMenuItem {

    private GUIItem item;
    private GuiAction action;
    private String actionData;

    public GuiMenuItem(GUIItem item, GuiAction action, String actionData){
        this.item = item;
        this.action = action;
        this.actionData = actionData;
    }

    public GUIItem buildItem(){

        item.setInventoryClickEvent(event -> {

            if(action.equals(GuiAction.EXECUTE_COMMAND)){
                ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();

                actionData = actionData.replace("{player-executor}", null);

                Bukkit.dispatchCommand(consoleCommandSender, actionData);
            }
            if(action.equals(GuiAction.SEND_TEXT)){
                actionData = actionData.replace("{player-executor}", null);
                //send player text
            }
            if(action.equals(GuiAction.NONE)){
                //do nothing
            }

        });

        return item;
    }

}
