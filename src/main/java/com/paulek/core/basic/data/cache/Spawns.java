package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLRandomTeleportButtonsData;
import com.paulek.core.basic.skin.Skin;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Spawns implements Cache<Vector3D, String> {

    private Core core;
    private Data<Vector3D, String> spawnsData;
    private DataModel dataModel;
    private Map<String, Vector3D> cachedSpawns = new ConcurrentHashMap<>(100);

    public Spawns(Core core, DataModel dataModel) {
        this.dataModel = dataModel;
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        switch (dataModel) {
            case MYSQL:spawnsData = null;
            case SQLITE: spawnsData = null;
            case FLAT: spawnsData = null;
        }
        assert spawnsData != null;
        spawnsData.load();
    }

    @Override
    public Vector3D get(String name) {
        return cachedSpawns.get(name);
    }

    @Override
    public void add(String name, Vector3D vector3D) {
        cachedSpawns.put(name, vector3D);
        spawnsData.save(vector3D);
    }

    @Override
    public void delete(String name) {
        cachedSpawns.remove(name);
        spawnsData.delete(name);
    }

    public Collection<String> getAllSpawnNames() {
        return cachedSpawns.keySet();
    }
}
