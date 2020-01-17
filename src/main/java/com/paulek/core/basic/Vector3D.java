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
    private float pitch;
    private float yaw;

    public Vector3D(UUID world, double x, double y, double z) {
        this.world = Bukkit.getWorld(world);
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = -256;
        this.yaw = -256;
    }

    public Vector3D(World world, double x, double y, double z, float pitch, float yaw) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Vector3D(Location location) {
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
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

    public Location asLocation() {
        if(pitch == -256 || yaw == -256) {
            return new Location(world, x, y, z);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        World world = this.world == null ? null : Bukkit.getWorlds().get(0);
        if(pitch == -256 || yaw == -256) {
            return "Location{world=" + world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + '}';
        }
        return "Location{world=" + world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",yaw=" + this.yaw + ",pitch=" +this.pitch + '}';
    }
}
