package com.paulek.core.basic.data.cache.models.mysql;

import com.google.common.base.Charsets;
import com.paulek.core.Core;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `buttons` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` LONGTEXT NOT NULL , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , PRIMARY KEY (`id`))");
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM buttons");
            ResultSet resultSet = preparedStatement1.executeQuery();
            Map<UUID, Vector3D> map = new HashMap<>();
            while (resultSet.next()) {
                Vector3D vector3D = deserializeData(resultSet);
                map.put(UUID.nameUUIDFromBytes(("rtp" + vector3D.toString()).getBytes(Charsets.UTF_8)), vector3D);
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
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM buttons WHERE uuid=?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not delete random teleport button location in database or load it to cache: ", exception);
        }
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
        if(vector3D == null) {
            return null;
        }
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO buttons SET uuid=?, world=?, x=?, y=?, z=?");
            preparedStatement.setString(1, UUID.nameUUIDFromBytes(("rtp" + vector3D.toString()).getBytes(Charsets.UTF_8)).toString());
            preparedStatement.setString(2, vector3D.getWorld().getUID().toString());
            preparedStatement.setDouble(3, vector3D.getX());
            preparedStatement.setDouble(4, vector3D.getX());
            preparedStatement.setDouble(5, vector3D.getX());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not save skin data from database: ", exception);
        }
        return null;
    }

    @Override
    public Vector3D deserializeData(ResultSet resultSet) {
        if(resultSet == null) return null;
        try {
            UUID worldUUID = UUID.fromString(resultSet.getString("world"));
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");
            double z = resultSet.getDouble("z");
            return new Vector3D(worldUUID, x, y, z);
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could load random teleport button data from database: ", exception);
        }
        return null;
    }
}
