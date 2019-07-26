package com.paulek.core.basic.data.databaseStorage;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Storage;
import com.paulek.core.basic.database.Database;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Rtps {

    private List<Location> buttons;

    private Core core;

    public Rtps(Core core){
        this.core = Objects.requireNonNull(core, "Core");
        buttons = new ArrayList<>();
    }

    public List getButtons() {
        return buttons;
    }

    public void add(Location location) {
        buttons.add(location);
        saveObjectToDatabase(location, core.getDatabase());
    }

    public void remove(Location location) {
        buttons.remove(location);
        removeFromDatabase(location, core.getDatabase());
    }

    public void loadFromDatabase(Database database) {
        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloud_buttons");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                World world = Bukkit.getWorld(UUID.fromString(resultSet.getString("world")));
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");

                Location location = new Location(world, x, y, z);

                buttons.add(location);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFromDatabase(Location location, Database database) {
        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cloud_buttons WHERE world=?, x=?, y=?, z=?");

            preparedStatement.setString(1, location.getWorld().getUID().toString());
            preparedStatement.setDouble(2, location.getX());
            preparedStatement.setDouble(3, location.getX());
            preparedStatement.setDouble(4, location.getX());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        loadFromDatabase(core.getDatabase());
    }

    public void saveObjectToDatabase(Location location, Database database) {
        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloud_buttons (world, x, y, z) VALUES (?, ?, ?, ?) " + core.getUpdateMethod() + " world=?, x=?, y=?, z=?");

            preparedStatement.setString(1, location.getWorld().getUID().toString());
            preparedStatement.setDouble(2, location.getX());
            preparedStatement.setDouble(3, location.getX());
            preparedStatement.setDouble(4, location.getX());

            preparedStatement.setString(5, location.getWorld().getUID().toString());
            preparedStatement.setDouble(6, location.getX());
            preparedStatement.setDouble(7, location.getX());
            preparedStatement.setDouble(8, location.getX());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reload(Database database) {
        buttons = null;
        buttons = new ArrayList<>();
        loadFromDatabase(database);
    }
}
