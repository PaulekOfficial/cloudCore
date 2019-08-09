package com.paulek.core.basic.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WorldGenerationEndEvent extends Event {

    public WorldGenerationEndEvent() {
        super();
    }

    public WorldGenerationEndEvent(boolean isAsync) {
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
}
