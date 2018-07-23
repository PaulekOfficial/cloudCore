package com.paulek.core.data.objects;

import java.util.UUID;

public class PlayerObject {

    private UUID uuid;
    private String nick;
    private long curenttimemilirs;

    public PlayerObject(UUID uuid, String nick){
        this.uuid = uuid;
        this.nick = nick;
        this.curenttimemilirs = System.currentTimeMillis();
    }

    public PlayerObject(UUID uuid, String nick, long curenttimemilirs){
        this.uuid = uuid;
        this.nick = nick;
        this.curenttimemilirs = curenttimemilirs;
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

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getCurenttimemilirs() {
        return curenttimemilirs;
    }

    public void setCurenttimemilirs(long curenttimemilirs) {
        this.curenttimemilirs = curenttimemilirs;
    }
}
