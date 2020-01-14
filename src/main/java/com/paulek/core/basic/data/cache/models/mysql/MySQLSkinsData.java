package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;
import com.paulek.core.basic.skin.Skin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Skin deserializeData(ResultSet resultSet) {
        return null;
    }
}
