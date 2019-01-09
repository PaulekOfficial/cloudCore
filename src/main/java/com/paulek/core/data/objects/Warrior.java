package com.paulek.core.data.objects;

import java.util.UUID;

public class Warrior {

    private UUID uuid;
    private String nick;
    private long curenttimemilirs;

    public Warrior(UUID uuid, String nick){
        this.uuid = uuid;
        this.nick = nick;
        this.curenttimemilirs = System.currentTimeMillis();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNick() {
        return nick;
    }

    public long getCurenttimemilirs() {
        return curenttimemilirs;
    }

    public void setCurenttimemilirs(long curenttimemilirs) {
        this.curenttimemilirs = curenttimemilirs;
    }
}
