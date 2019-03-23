package com.paulek.core.basic;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager {

    public boolean chatEnabled = true;
    public HashMap<UUID, Long> muted = new HashMap<UUID, Long>();


    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public HashMap<UUID, Long> getMuted() {
        return muted;
    }

    public void setMuted(HashMap<UUID, Long> muted) {
        this.muted = muted;
    }
}
