package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;

import java.sql.ResultSet;
import java.util.UUID;

public class MySQLUserData implements Data<User, UUID>, SQLDataModel<User> {

    @Override
    public void load() {

    }

    @Override
    public User load(UUID o) {
        return null;
    }

    @Override
    public void save(boolean ignoreNotChanged) {

    }

    @Override
    public ResultSet serializeData(User user) {
        return null;
    }

    @Override
    public User deserializeData(ResultSet resultSet) {
        return null;
    }
}
