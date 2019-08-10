package com.paulek.core.basic.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCombatStartEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player attacked;
    private Entity attacker;

    private boolean cancelled = false;

    public PlayerCombatStartEvent(Player attacked, Entity attacker) {
        this.attacked = attacked;
        this.attacker = attacker;
    }

    public PlayerCombatStartEvent(boolean isAsync) {
        super(isAsync);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
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

    public Player getAttacked() {
        return attacked;
    }

    public Entity getAttacker() {
        return attacker;
    }
}
