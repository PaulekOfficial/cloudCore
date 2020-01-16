package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;
import com.paulek.core.basic.skin.Skin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;

public class MySQLRandomTeleportButtonsData implements Data<Vector3D, UUID>, SQLDataModel<Vector3D> {

    private Core core;

    public MySQLRandomTeleportButtonsData(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    @Override
    public Vector3D load(UUID uuid) {
        return null;
    }

    @Override
    public Vector3D load(int id) {
        return null;
    }

    @Override
    public void load() {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `buttons` ( `id` INT NOT NULL AUTO_INCREMENT , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , PRIMARY KEY (`id`))");
            preparedStatement.executeQuery();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM buttons");
            ResultSet resultSet = preparedStatement1.executeQuery();
            Map<UUID, Vector3D> map = new HashMap<>();
            while (resultSet.next()) {
                Vector3D vector3D = deserializeData(resultSet);
                map.put(UUID.fromString(vector3D.toString()), vector3D);
            }
            core.getRandomTeleportButtonsStorage().addToCache(map);
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not create new random teleport buttons table in database or load it to cache: ", exception);
        }
    }

    @Override
    public void save(Collection<Vector3D> collection, boolean ignoreNotChanged) {
        return;
    }

    @Override
    public void save(Vector3D vector3D) {
        serializeData(vector3D);
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void delete(int id) {
        return;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public ResultSet serializeData(Vector3D vector3D) {
        return null;
    }

    @Override
    public Vector3D deserializeData(ResultSet resultSet) {
        return null;
    }
}
