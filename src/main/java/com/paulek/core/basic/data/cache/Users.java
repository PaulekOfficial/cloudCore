package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLUserData;

import java.util.Objects;
import java.util.UUID;

public class Users implements Cache<User, UUID> {

    private Core core;
    private Data usersData;
    private DataModel dataModel;

    public Users(Core core, DataModel dataModel) {
        this.core = Objects.requireNonNull(core, "core");
        this.dataModel = Objects.requireNonNull(dataModel, "dataModel");
    }

    public void init() {
        usersData = switch (dataModel) {
            case MYSQL -> new MySQLUserData();
            case SQLITE -> null;
            case FLAT -> null;
        };
        assert usersData != null;
        usersData.load();
    }

    @Override
    public User get(UUID uuid) {
        return null;
    }

    @Override
    public void add(UUID uuid, User user) {

    }

    @Override
    public void delete(UUID uuid) {

    }

}
