package com.paulek.core.basic.data.databaseStorage;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.Storage;
import com.paulek.core.basic.database.Database;
import com.paulek.core.common.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Users extends Storage {

    private Map<UUID, User> users;
    private Core core;

    public Users(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @Override
    public void saveAllToDatabase(Database database) {
        for(User user : users.values()){
            saveObjectToDatabase(user, database);
        }
    }

    @Override
    public void saveDirtyObjects(Database database) {
        if(users == null || users.isEmpty()) return;

        for(User user : users.values()){
            if(user.isDirty()){
                saveObjectToDatabase(user, database);
                user.setDirty(false);
            }
        }
    }

    public void checkPlayer(Player player){
        if(!users.containsKey(player.getUniqueId())){
            User user = new User(player.getUniqueId(), player.getDisplayName(), player.getLocation(), player.getLocation(), player.getAddress().getAddress(), new HashMap<>(), LocalDateTime.now(), false, false, false, false, core);
            user.setDirty(true);
            users.put(player.getUniqueId(), user);
            core.getConsoleLog().info("Created new " + user.getUuid().toString() + " user");
        }
    }

    @Override
    public void loadFromDatabase(Database database) {
        try(Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloud_users");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                try {

                    UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                    String lastAccountName = resultSet.getString("lastAccountName");
                    Location logoutlocation = LocationSerializer.locationFromString(resultSet.getString("logoutLocation"));
                    Location lastlocation = LocationSerializer.locationFromString(resultSet.getString("lastLocation"));
                    InetAddress ipAddres = InetAddress.getByName(resultSet.getString("ipAddres"));
                    Map<String, Location> homes = LocationSerializer.locationMapFormString(resultSet.getString("homes"));
                    LocalDateTime lastActivity = resultSet.getTimestamp("lastActivity").toLocalDateTime();
                    boolean socialSpy = resultSet.getBoolean("socialSpy");
                    boolean vanish = resultSet.getBoolean("vanish");
                    boolean tpToogle = resultSet.getBoolean("tpToogle");
                    boolean tpsMonitor = resultSet.getBoolean("tpsMonitor");

                    User user = new User(uuid, lastAccountName, logoutlocation, lastlocation, ipAddres, homes, lastActivity, socialSpy, vanish, tpToogle, tpsMonitor, core);

                    users.put(uuid, user);

                } catch (UnknownHostException exception){
                    exception.printStackTrace();
                }

            }
            preparedStatement.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveObjectToDatabase(Object object, Database database) {
        User user = (User) object;
        try(Connection connection = database.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloud_users (uuid, lastAccountName, logoutLocation, lastLocation, ipAddres, homes, lastActivity, socialSpy, vanish, tpToogle, tpsMonitor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + core.getUpdateMethod() + " lastAccountName=?, logoutLocation=?, lastLocation=?, ipAddres=?, homes=?, lastActivity=?, socialSpy=?, vanish=?, tpToogle=?, tpsMonitor=?");

            preparedStatement.setString(1, user.getUuid().toString());
            preparedStatement.setString(2, user.getLastAccountName());
            preparedStatement.setString(3, LocationSerializer.locationToString(user.getLogoutlocation()));
            preparedStatement.setString(4, LocationSerializer.locationToString(user.getLastlocation()));
            preparedStatement.setString(5, user.getIpAddres().getHostAddress());
            preparedStatement.setString(6, LocationSerializer.locationMapToString(user.getHomes()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(user.getLastActivity()));
            preparedStatement.setBoolean(8, user.isSocialSpy());
            preparedStatement.setBoolean(9, user.isVanish());
            preparedStatement.setBoolean(10, user.isTpToogle());
            preparedStatement.setBoolean(11, user.isTpsMonitor());

            preparedStatement.setString(12, user.getLastAccountName());
            preparedStatement.setString(13, LocationSerializer.locationToString(user.getLogoutlocation()));
            preparedStatement.setString(14, LocationSerializer.locationToString(user.getLastlocation()));
            preparedStatement.setString(15, user.getIpAddres().getHostAddress());
            preparedStatement.setString(16, LocationSerializer.locationMapToString(user.getHomes()));
            preparedStatement.setTimestamp(17, Timestamp.valueOf(user.getLastActivity()));
            preparedStatement.setBoolean(18, user.isSocialSpy());
            preparedStatement.setBoolean(19, user.isVanish());
            preparedStatement.setBoolean(20, user.isTpToogle());
            preparedStatement.setBoolean(21, user.isTpsMonitor());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void init() {
        users = new HashMap<>();
        loadFromDatabase(core.getDatabase());
    }

    public Map<UUID, User> getUsers() {
        return users;
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }
}
