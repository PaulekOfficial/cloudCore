package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Pair;
import com.paulek.core.basic.TeleportRequstType;
import com.paulek.core.basic.TriplePackage;
import com.paulek.core.basic.data.Cache;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportRequests implements Cache<TriplePackage<UUID, LocalDateTime, TeleportRequstType>, UUID> {
    private Core core;
    private Map<UUID, TriplePackage<UUID, LocalDateTime, TeleportRequstType>> cachedTeleportRequests = new ConcurrentHashMap<>(100);
    private Map<UUID, Integer> teleportDelay = new ConcurrentHashMap<>();

    public TeleportRequests(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(core, run -> {
            Iterator<UUID> iterator = cachedTeleportRequests.keySet().iterator();
            while(iterator.hasNext()) {
                UUID uuid = iterator.next();
                LocalDateTime time = cachedTeleportRequests.get(uuid).getD();
                if(time.until(LocalDateTime.now(), ChronoUnit.SECONDS) >= core.getConfiguration().tpaRejectTime) {
                    iterator.remove();
                }
            }
        }, 0, 20 * 60);
    }

    public int getTeleportDelay(UUID uuid) {
        return teleportDelay.getOrDefault(uuid, -1);
    }

    public void deleteTeleportDelay(UUID uuid) {
        teleportDelay.remove(uuid);
    }

    public void addTeleportDelay(UUID uuid, int id) {
        teleportDelay.put(uuid, id);
    }

    @Override
    public TriplePackage<UUID, LocalDateTime, TeleportRequstType> get(UUID uuid) {
        return cachedTeleportRequests.get(uuid);
    }

    @Override
    public void add(UUID uuid, TriplePackage<UUID, LocalDateTime, TeleportRequstType> pair) {
        cachedTeleportRequests.put(uuid, pair);
    }

    @Override
    public void delete(UUID uuid) {
        cachedTeleportRequests.remove(uuid);
    }
}
