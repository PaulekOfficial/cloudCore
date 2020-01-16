package com.paulek.core.basic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Vector3D {

    private World world;
    private double x;
    private double y;
    private double z;

    public Vector3D(UUID world, double x, double y, double z) {
        this.world = Bukkit.getWorld(world);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Location location) {
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        World world = this.world == null ? null : Bukkit.getWorlds().get(0);
        return "Location{world=" + world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + '}';
    }
}
