package com.paulek.core.basic.event;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRandomTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private World world;
    private int maxLocX;
    private int maxLocZ;
    private int centerLocX;
    private int centerLocZ;

    public PlayerRandomTeleportEvent(Player player, World world, int maxLocX, int maxLocZ, int centerLocX, int centerLocZ) {
        this.player = player;
        this.world = world;
        this.maxLocX = maxLocX;
        this.maxLocZ = maxLocZ;
        this.centerLocX = centerLocX;
        this.centerLocZ = centerLocZ;
    }

    public PlayerRandomTeleportEvent(boolean isAsync) {
        super(isAsync);
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

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public int getMaxLocX() {
        return maxLocX;
    }

    public int getMaxLocZ() {
        return maxLocZ;
    }

    public int getCenterLocX() {
        return centerLocX;
    }

    public int getCenterLocZ() {
        return centerLocZ;
    }
}
