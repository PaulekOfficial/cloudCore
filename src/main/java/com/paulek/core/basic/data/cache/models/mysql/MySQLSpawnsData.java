package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.Core;
import com.paulek.core.basic.Pair;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class MySQLSpawnsData implements Data<Pair<String, Vector3D>, String>, SQLDataModel<Pair<String, Vector3D>> {

    private Core core;

    public MySQLSpawnsData(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    @Override
    public Pair<String, Vector3D> load(String s) {
        return null;
    }

    @Override
    public Pair<String, Vector3D> load(int id) {
        return null;
    }

    @Override
    public void load() {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + core.getConfiguration().tablePrefix + "spawns ( `id` INT NOT NULL AUTO_INCREMENT , `name` TEXT NOT NULL , `world` TEXT NOT NULL ,`x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , `pitch` FLOAT NOT NULL , `yaw` FLOAT NOT NULL , PRIMARY KEY (`id`))");
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM " + core.getConfiguration().tablePrefix + "spawns");
            ResultSet resultSet = preparedStatement1.executeQuery();
            Map<String, Vector3D> spawnsMap = new HashMap<>();
            while (resultSet.next()) {
                Pair<String, Vector3D> pair = deserializeData(resultSet);
                if(pair != null) {
                    spawnsMap.put(pair.getT(), pair.getR());
                }
            }
            core.getSpawnsStorage().addToCache(spawnsMap);
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not create new spawns table or download content form table in database: ", exception);
        }
    }

    @Override
    public void save(Collection<Pair<String, Vector3D>> collection, boolean ignoreNotChanged) {
        return;
    }

    @Override
    public void save(Pair<String, Vector3D> vector3D) {
        serializeData(vector3D);
    }

    @Override
    public void delete(String s) {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + core.getConfiguration().tablePrefix + "spawns WHERE name=?");
            preparedStatement.setString(1, s);
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
    public ResultSet serializeData(Pair<String, Vector3D> vector3D) {
        if(vector3D == null) {
            return null;
        }
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + core.getConfiguration().tablePrefix + "spawns SET name=?, world=?, x=?, y=?, z=?, pitch=?, yaw=?");
            Vector3D vector3D1 = vector3D.getR();
            preparedStatement.setString(1, vector3D.getT());
            preparedStatement.setString(2, vector3D1.getWorld().getUID().toString());
            preparedStatement.setDouble(3, vector3D1.getX());
            preparedStatement.setDouble(4, vector3D1.getY());
            preparedStatement.setDouble(5, vector3D1.getZ());
            preparedStatement.setFloat(6, vector3D1.getPitch());
            preparedStatement.setFloat(7, vector3D1.getYaw());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not save spawn data from database: ", exception);
        }
        return null;
    }

    @Override
    public Pair<String, Vector3D> deserializeData(ResultSet resultSet) {
        if(resultSet == null) return null;
        try {
            String name = resultSet.getString("name");
            UUID worldUUID = UUID.fromString(resultSet.getString("world"));
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");
            double z = resultSet.getDouble("z");
            float pitch = resultSet.getFloat("pitch");
            float yaw = resultSet.getFloat("yaw");
            return new Pair<>(name, new Vector3D(worldUUID, x, y, z, pitch, yaw));
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could load spawn data from database: ", exception);
        }
        return null;
    }
}
