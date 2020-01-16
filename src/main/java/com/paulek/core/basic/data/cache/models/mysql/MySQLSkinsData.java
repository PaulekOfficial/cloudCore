package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;
import com.paulek.core.basic.skin.Skin;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class MySQLSkinsData implements Data<Skin, UUID>, SQLDataModel<Skin> {

    private Core core;

    public MySQLSkinsData(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    @Override
    public void delete(UUID uuid) {
        return;
    }

    @Override
    public void delete(int id) {
        return;
    }

    @Override
    public Skin load(int id) {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM skins WHERE id=?");
            preparedStatement.setInt(1, id);
            Skin skin = deserializeData(preparedStatement.executeQuery());
            if(skin != null) {
                return skin;
            }
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not load skin (" + id + ") data from database: ", exception);
        }
        return null;
    }

    @Override
    public Skin load(UUID uuid) {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM skins WHERE uuid=?");
            preparedStatement.setString(1, uuid.toString());
            Skin skin = deserializeData(preparedStatement.executeQuery());
            if(skin != null) {
                return skin;
            }
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not load skin (" + uuid.toString() + ") data from database: ", exception);
        }
        return null;
    }

    @Override
    public int count() {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM skins");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not get skins count data from database: ", exception);
        }
        return 0;
    }

    @Override
    public void load() {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `skins` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `manuallySet` TINYINT(1) , PRIMARY KEY (`id`))");
            preparedStatement.executeQuery();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not create new skins table in database: ", exception);
        }
    }

    @Override
    public void save(Collection<Skin> collection, boolean ignoreNotChanged) {
        for(Skin skin : collection) {
            if(skin.isDirty() || ignoreNotChanged) {
                save(skin);
                skin.setDirty(false);
            }
        }
    }

    @Override
    public void save(Skin skin) {
        serializeData(skin);
    }

    @Override
    public ResultSet serializeData(Skin skin) {
        if(skin == null) {
            return null;
        }
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO skins SET uuid=?, name=?, value=?, signature=?, lastUpdate=?, manuallySet=? ON DUPLICATE uuid UPDATE uuid=?, name=?, value=?, signature=?, lastUpdate=?, manuallySet=?");
            preparedStatement.setString(1, skin.getUuid().toString());
            preparedStatement.setString(2, skin.getName());
            preparedStatement.setString(3, skin.getValue());
            preparedStatement.setString(4, skin.getSignature());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(skin.getLastUpdate()));
            preparedStatement.setBoolean(6, skin.isManuallySet());
            preparedStatement.setString(7, skin.getUuid().toString());
            preparedStatement.setString(8, skin.getName());
            preparedStatement.setString(9, skin.getValue());
            preparedStatement.setString(10, skin.getSignature());
            preparedStatement.setTimestamp(11, Timestamp.valueOf(skin.getLastUpdate()));
            preparedStatement.setBoolean(12, skin.isManuallySet());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not save skin data from database: ", exception);
        }
        return null;
    }

    @Override
    public Skin deserializeData(ResultSet resultSet) {
        if(resultSet == null) return null;
        try {
            if(resultSet.next()) return null;
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            String name = resultSet.getString("name");
            String value = resultSet.getString("value");
            String signature = resultSet.getString("signature");
            LocalDateTime time = resultSet.getTimestamp("lastUpdate").toLocalDateTime();
            boolean manuallySet = resultSet.getBoolean("manuallySet");
            return new Skin(uuid, name, value, signature, time, manuallySet, core, false);
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could load skin data from database: ", exception);
        }
        return null;
    }
}
