package com.paulek.core.basic.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCombatEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    public PlayerCombatEndEvent(Player player) {
        this.player = player;
    }

    public PlayerCombatEndEvent(boolean isAsync) {
        super(isAsync);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
