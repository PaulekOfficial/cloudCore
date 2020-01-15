package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Pair;
import com.paulek.core.basic.data.Cache;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportRequests implements Cache<Pair<UUID, LocalDateTime>, UUID> {
    private Core core;
    private Map<UUID, Pair<UUID, LocalDateTime>> cachedTeleportRequests = new ConcurrentHashMap<>(100);

    public TeleportRequests(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(core, run -> {
            Iterator<UUID> iterator = cachedTeleportRequests.keySet().iterator();
            while(iterator.hasNext()) {
                UUID uuid = iterator.next();
                LocalDateTime time = cachedTeleportRequests.get(uuid).getR();
                if(time.until(LocalDateTime.now(), ChronoUnit.SECONDS) >= core.getConfiguration().tpaRejectTime) {
                    iterator.remove();
                }
            }
        }, 0, 20 * 60);
    }

    @Override
    public Pair<UUID, LocalDateTime> get(UUID uuid) {
        return cachedTeleportRequests.get(uuid);
    }

    @Override
    public void add(UUID uuid, Pair<UUID, LocalDateTime> pair) {
        cachedTeleportRequests.put(uuid, pair);
    }

    @Override
    public void delete(UUID uuid) {
        cachedTeleportRequests.remove(uuid);
    }
}
