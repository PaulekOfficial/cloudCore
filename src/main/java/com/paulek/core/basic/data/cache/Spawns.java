package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.Pair;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLSpawnsData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Spawns implements Cache<Vector3D, String> {

    private Core core;
    private Data<Pair<String, Vector3D>, String> spawnsData;
    private DataModel dataModel;
    private Map<String, Vector3D> cachedSpawns = new ConcurrentHashMap<>(100);

    public Spawns(Core core, DataModel dataModel) {
        this.dataModel = dataModel;
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        switch (dataModel) {
            case MYSQL: spawnsData = new MySQLSpawnsData(core);
            case SQLITE: spawnsData = null;
            case FLAT: spawnsData = null;
            default: spawnsData = new MySQLSpawnsData(core);
        }
        spawnsData.load();
    }

    @Override
    public Vector3D get(String name) {
        return cachedSpawns.get(name);
    }

    @Override
    public void add(String name, Vector3D vector3D) {
        cachedSpawns.put(name, vector3D);
        spawnsData.save(new Pair<>(name, vector3D));
    }

    @Override
    public void delete(String name) {
        cachedSpawns.remove(name);
        spawnsData.delete(name);
    }

    public void addToCache(Map<String, Vector3D> stringVector3DMap) {
        cachedSpawns.putAll(stringVector3DMap);
    }

    public Set<String> getAllSpawnNames() {
        return cachedSpawns.keySet();
    }
}
