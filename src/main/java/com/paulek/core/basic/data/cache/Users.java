package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLUserData;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Users implements Cache<User, UUID> {

    private Core core;
    private Data<User, UUID> usersData;
    private DataModel dataModel;

    private Map<UUID, User> cachedUsers = new ConcurrentHashMap<>(100);

    public Users(Core core, DataModel dataModel) {
        this.core = Objects.requireNonNull(core, "core");
        this.dataModel = Objects.requireNonNull(dataModel, "dataModel");
    }

    public void init() {
        usersData = switch (dataModel) {
            case MYSQL -> new MySQLUserData(core);
            case SQLITE -> null;
            case FLAT -> null;
        };
        assert usersData != null;
        usersData.load();
        Bukkit.getScheduler().runTaskTimerAsynchronously(core.getPlugin(), run -> {
            usersData.save(cachedUsers.values(), false);
        }, 0, 20 * 60);
    }

    public void saveAndDelete(UUID uuid) {
        usersData.save(cachedUsers.get(uuid));
        delete(uuid);
    }

    public void saveDataBeforeShutdown() {
        usersData.save(cachedUsers.values(), true);
    }

    @Override
    public User get(UUID uuid) {
        if(cachedUsers.containsKey(uuid)) {
            return cachedUsers.get(uuid);
        }
        User user = usersData.load(uuid);
        if(user != null) {
            add(uuid, user);
        }
        return null;
    }

    @Override
    public void add(UUID uuid, User user) {
        cachedUsers.put(uuid, user);
    }

    @Override
    public void delete(UUID uuid) {
        cachedUsers.remove(uuid);
    }

}