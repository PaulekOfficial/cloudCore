package com.paulek.core.basic;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager {

    public static boolean chatEnabled = true;
    public static HashMap<UUID, Long> muted = new HashMap<UUID, Long>();


    public static boolean isChatEnabled() {
        return chatEnabled;
    }

    public static void setChatEnabled(boolean chatEnabled) {
        ChatManager.chatEnabled = chatEnabled;
    }

    public static HashMap<UUID, Long> getMuted() {
        return muted;
    }

    public static void setMuted(HashMap<UUID, Long> muted) {
        ChatManager.muted = muted;
    }
}
