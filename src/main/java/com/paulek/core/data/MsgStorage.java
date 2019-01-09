package com.paulek.core.data;

import java.util.HashMap;
import java.util.UUID;

public class MsgStorage {

    private static HashMap<UUID, String> messages = new HashMap<UUID, String>();

    public static HashMap<UUID, String> getMessages() {
        return messages;
    }

    public static void setMessages(HashMap<UUID, String> messages) {
        MsgStorage.messages = messages;
    }
}
