package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Timestamp;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Timestamps implements Cache<Timestamp, UUID> {

    private Core core;
    private Data<Timestamp, UUID> timestampsData;
    private DataModel dataModel;
    private Map<String, Vector3D> cachedTimestamps = new ConcurrentHashMap<>(100);

    public Timestamps(Core core, DataModel dataModel) {
        this.dataModel = dataModel;
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        timestampsData = switch (dataModel) {
            case MYSQL -> null;
            case SQLITE -> null;
            case FLAT -> null;
        };
        assert timestampsData != null;
        timestampsData.load();
    }

    @Override
    public Timestamp get(UUID uuid) {
        return null;
    }

    @Override
    public void add(UUID uuid, Timestamp timestamp) {

    }

    @Override
    public void delete(UUID uuid) {

    }
}
