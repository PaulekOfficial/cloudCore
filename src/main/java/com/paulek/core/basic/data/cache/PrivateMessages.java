package com.paulek.core.basic.data.cache;

import com.paulek.core.basic.data.Cache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PrivateMessages implements Cache<UUID, UUID> {

    private Map<UUID, UUID> cachedRespond = new ConcurrentHashMap<>(100);

    @Override
    public UUID get(UUID uuid) {
        return cachedRespond.get(uuid);
    }


    @Override
    public void add(UUID receiver, UUID sender) {
        cachedRespond.put(receiver, sender);
    }

    @Override
    public void delete(UUID uuid) {
        cachedRespond.remove(uuid);
        cachedRespond.values().removeIf(uuid1 -> uuid1.equals(uuid));
    }
}
