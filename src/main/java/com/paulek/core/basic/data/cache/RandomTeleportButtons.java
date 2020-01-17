package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLRandomTeleportButtonsData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RandomTeleportButtons implements Cache<Vector3D, UUID> {

    private Core core;
    private Data<Vector3D, UUID> randomTeleportButtonsData;
    private DataModel dataModel;

    private Map<UUID, Vector3D> cachedLocations = new ConcurrentHashMap<>(10);

    public RandomTeleportButtons(Core core, DataModel dataModel) {
        this.dataModel = dataModel;
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        randomTeleportButtonsData = switch (dataModel) {
            case MYSQL -> new MySQLRandomTeleportButtonsData(core);
            case SQLITE -> null;
            case FLAT -> null;
        };
        assert randomTeleportButtonsData != null;
        randomTeleportButtonsData.load();
    }

    @Override
    public Vector3D get(UUID uuid) {
        return cachedLocations.get(uuid);
    }

    @Override
    public void add(UUID uuid, Vector3D vector3D) {
        cachedLocations.put(uuid, vector3D);
        randomTeleportButtonsData.save(vector3D);
    }

    public void addToCache(Map<UUID, Vector3D> uuidVector3DMap) {
        cachedLocations.putAll(uuidVector3DMap);
    }

    @Override
    public void delete(UUID uuid) {
        cachedLocations.remove(uuid);
        randomTeleportButtonsData.delete(uuid);
    }
}
