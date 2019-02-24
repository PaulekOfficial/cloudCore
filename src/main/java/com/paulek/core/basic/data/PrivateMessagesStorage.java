package com.paulek.core.basic.data;

import java.util.HashMap;
import java.util.UUID;

public class PrivateMessagesStorage {

    private static HashMap<UUID, String> messages = new HashMap<UUID, String>();

    public static HashMap<UUID, String> getMessages() {
        return messages;
    }

    public static void setMessages(HashMap<UUID, String> messages) {
        PrivateMessagesStorage.messages = messages;
    }
}
