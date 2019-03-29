package com.paulek.core.basic.data;

import java.util.HashMap;
import java.util.UUID;

public class Pms {

    private HashMap<UUID, String> messages = new HashMap<UUID, String>();

    public HashMap<UUID, String> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<UUID, String> messages) {
        messages = messages;
    }
}
