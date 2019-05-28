package com.paulek.core.basic.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCombatStartEvent extends Event {

    private Player attacked;
    private Entity attacker;

    public PlayerCombatStartEvent(Player attacked, Entity attacker) {
        this.attacked = attacked;
        this.attacker = attacker;
    }

    public PlayerCombatStartEvent(boolean isAsync) {
        super(isAsync);
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Player getAttacked() {
        return attacked;
    }

    public Entity getAttacker() {
        return attacker;
    }
}
