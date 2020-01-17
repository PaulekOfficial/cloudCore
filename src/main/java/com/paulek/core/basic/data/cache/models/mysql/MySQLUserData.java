package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;
import com.paulek.core.common.LocationUtil;
import org.bukkit.Location;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class MySQLUserData implements Data<User, UUID>, SQLDataModel<User> {

    private Core core;

    public MySQLUserData(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public User load(int id) {
        return null;
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
    public void load() {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL , `godMode` TINYINT NOT NULL , PRIMARY KEY (`id`))");
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not create new users table in database: ", exception);
        }
    }

    @Override
    public User load(UUID uuid) {
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE uuid=?");
            preparedStatement.setString(1, uuid.toString());
            User user = deserializeData(preparedStatement.executeQuery());
            if(user != null) {
                return user;
            }
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not load user (" + uuid.toString() + ") data from database: ", exception);
        }
        return null;
    }

    @Override
    public void save(Collection<User> userCollection, boolean ignoreNotChanged) {
        for(User user : userCollection) {
            if(user.isDirty() || ignoreNotChanged) {
                save(user);
                user.setDirty(false);
            }
        }
    }

    @Override
    public void save(User user) {
        serializeData(user);
    }

    @Override
    public ResultSet serializeData(User user) {
        if (user == null) {
            return null;
        }
        if(user.getLastActivity() == null) {
            user.setLastActivity(LocalDateTime.now());
            user.setDirty(false);
            try(Connection connection = core.getDatabase().getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users SET lastAccountName=?, logoutlocation=?, lastlocation=?, ipAddres=?, homes=?, lastActivity=?, socialSpy=?, vanish=?, tpToogle=?, tpsMonitor=?, godMode=?, uuid=?");
                preparedStatement.setString(1, user.getLastAccountName());
                preparedStatement.setString(2, LocationUtil.locationToString(user.getLogoutlocation()));
                preparedStatement.setString(3, LocationUtil.locationToString(user.getLogoutlocation()));
                preparedStatement.setString(4, user.getIpAddres().getHostAddress());
                preparedStatement.setString(5, LocationUtil.locationMapToString(user.getHomes()));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getLastActivity()));
                preparedStatement.setBoolean(7, user.isSocialSpy());
                preparedStatement.setBoolean(8, user.isVanish());
                preparedStatement.setBoolean(9, user.isTpToogle());
                preparedStatement.setBoolean(10, user.isTpsMonitor());
                preparedStatement.setBoolean(11, user.isGodMode());
                preparedStatement.setString(12, user.getUuid().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                core.getLogger().log(Level.WARNING, "Could not save user (" + user.getUuid().toString() + ") data from database: ", exception);
            }
            return null;
        }
        try(Connection connection = core.getDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET lastAccountName=?, logoutlocation=?, lastlocation=?, ipAddres=?, homes=?, lastActivity=?, socialSpy=?, vanish=?, tpToogle=?, tpsMonitor=?, godMode=? WHERE uuid=?");
            preparedStatement.setString(1, user.getLastAccountName());
            preparedStatement.setString(2, LocationUtil.locationToString(user.getLogoutlocation()));
            preparedStatement.setString(3, LocationUtil.locationToString(user.getLogoutlocation()));
            preparedStatement.setString(4, user.getIpAddres().getHostAddress());
            preparedStatement.setString(5, LocationUtil.locationMapToString(user.getHomes()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getLastActivity()));
            preparedStatement.setBoolean(7, user.isSocialSpy());
            preparedStatement.setBoolean(8, user.isVanish());
            preparedStatement.setBoolean(9, user.isTpToogle());
            preparedStatement.setBoolean(10, user.isTpsMonitor());
            preparedStatement.setBoolean(11, user.isGodMode());
            preparedStatement.setString(12, user.getUuid().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            core.getLogger().log(Level.WARNING, "Could not save user (" + user.getUuid().toString() + ") data from database: ", exception);
        }
        return null;
    }

    @Override
    public User deserializeData(ResultSet resultSet) {
        if(resultSet == null) return null;
        try {
            if(resultSet.next()) return null;
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            String lastAccountName = resultSet.getString("lastAccountName");
            Location logoutlocation = LocationUtil.locationFromString(resultSet.getString("logoutLocation"));
            Location lastlocation = LocationUtil.locationFromString(resultSet.getString("lastLocation"));
            InetAddress ipAddres = InetAddress.getByName(resultSet.getString("ipAddres"));
            Map<String, Location> homes = LocationUtil.locationMapFormString(resultSet.getString("homes"));
            LocalDateTime lastActivity = resultSet.getTimestamp("lastActivity").toLocalDateTime();
            boolean socialSpy = resultSet.getBoolean("socialSpy");
            boolean vanish = resultSet.getBoolean("vanish");
            boolean tpToogle = resultSet.getBoolean("tpToogle");
            boolean tpsMonitor = resultSet.getBoolean("tpsMonitor");
            boolean godMode = resultSet.getBoolean("godMode");
            return new User(uuid, lastAccountName, logoutlocation, lastlocation, ipAddres, homes, lastActivity, socialSpy, vanish, tpToogle, tpsMonitor, godMode, core);
        } catch (SQLException | UnknownHostException exception) {
            core.getLogger().log(Level.WARNING, "Could load user data from database: ", exception);
        }
        return null;
    }
}
