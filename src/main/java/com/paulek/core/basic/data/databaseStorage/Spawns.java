package com.paulek.core.basic.data.databaseStorage;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Storage;
import com.paulek.core.basic.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Spawns extends Storage {

    private Map<String, Location> spawns;

    private Core core;

    public Spawns(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    public Location getSpawn(String name){
        return spawns.get(name);
    }

    @Override
    public void loadFromDatabase(Database database) {
        try(Connection connection = database.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloud_spawns");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                String name = resultSet.getString("name");
                String worldUID = resultSet.getString("world");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float pitch = resultSet.getFloat("pitch");
                float yaw = resultSet.getFloat("yaw");

                Location location = new Location(Bukkit.getWorld(UUID.fromString(worldUID)), x, y, z, pitch, yaw);
                spawns.put(name, location);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        spawns = new HashMap<>();
        loadFromDatabase(core.getDatabase());
    }

    public void addNewSpawn(String name, Location location){
        spawns.put(name, location);
        List<Object> list = new ArrayList<>();
        list.add(name);
        list.add(location);
        saveObjectToDatabase(list, core.getDatabase());
    }

    @Override
    public void saveObjectToDatabase(Object object, Database database) {

        List list = (ArrayList) object;

        try(Connection connection = database.getConnection()){

            String name = (String) list.get(0);
            Location location = (Location) list.get(1);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloud_spawns (name, world, x, y, z, pitch, yaw) VALUES (?, ?, ?, ?, ?, ?, ?) " + core.getUpdateMethod() + " name=?, world=?, x=?, y=?, z=?, pitch=?, yaw=?");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, location.getWorld().getUID().toString());
            preparedStatement.setDouble(3, location.getX());
            preparedStatement.setDouble(4, location.getY());
            preparedStatement.setDouble(5, location.getZ());
            preparedStatement.setFloat(6, location.getPitch());
            preparedStatement.setFloat(7, location.getYaw());

            preparedStatement.setString(8, name);
            preparedStatement.setString(9, location.getWorld().getUID().toString());
            preparedStatement.setDouble(10, location.getX());
            preparedStatement.setDouble(11, location.getY());
            preparedStatement.setDouble(12, location.getZ());
            preparedStatement.setFloat(13, location.getPitch());
            preparedStatement.setFloat(14, location.getYaw());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    //Not usefull
    @Override
    public void saveDirtyObjects(Database database) {

    }

    //Not usefull
    @Override
    public void saveAllToDatabase(Database database) {

    }

    @Override
    public void reload(Database database) {

    }


}
