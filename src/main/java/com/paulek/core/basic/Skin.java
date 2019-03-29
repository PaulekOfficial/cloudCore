package com.paulek.core.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.paulek.core.Core;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Skin {

    private String name;
    private String value;
    private String signature;
    private Long lastUpdate;
    private Core core;


    @JsonIgnore
    public Skin(String nick, String value, String signature, Long lastUpdate, Core core) {

        this.name = nick;
        this.value = value;
        this.signature = signature;
        this.lastUpdate = lastUpdate;
        this.core = Objects.requireNonNull(core, "Core");

    }

    @JsonIgnore
    public Skin(String name, String value, String signature, Core core) {

        this.name = name;
        this.value = value;
        this.signature = signature;
        this.lastUpdate = System.currentTimeMillis();
        this.core = Objects.requireNonNull(core, "Core");

    }

    @JsonIgnore
    public void applySkinForPlayers(Player player) {

        GameProfile gameProfile = ((CraftPlayer) player).getProfile();

        gameProfile.getProperties().clear();

        gameProfile.getProperties().put("textures", getProperty());

        Bukkit.getScheduler().runTask(core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    p.hidePlayer(player);
                    p.showPlayer(player);

                }
            }
        });

    }

    @JsonIgnore
    public void updateSkinForPlayer(Player player) {

        UUID uuid = player.getUniqueId();

        if (Bukkit.getPlayer(uuid) == null) return;

        EnumGamemode enumGamemode = EnumGamemode.getById(player.getGameMode().getValue());

        WorldType worldType = WorldType.getType(player.getWorld().getWorldType().getName());

        EnumDifficulty enumDifficulty = EnumDifficulty.getById(player.getWorld().getDifficulty().getValue());

        DimensionManager dimension;

        switch (player.getWorld().getEnvironment()) {
            case NORMAL:
                dimension = DimensionManager.OVERWORLD;
                break;
            case THE_END:
                dimension = DimensionManager.THE_END;
                break;
            case NETHER:
                dimension = DimensionManager.NETHER;
                break;
            default:
                dimension = DimensionManager.OVERWORLD;
                break;
        }

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_remove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle());

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());

        PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(dimension, enumDifficulty, worldType, enumGamemode);

        Location loc = player.getLocation();

        PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<>(), 0);

        Bukkit.getScheduler().runTaskLater(core.getPlugin(), () -> {

            boolean fly = player.isFlying();

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo_remove);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo_add);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutRespawn);

            if (player.isFlying()) player.setFlying(true);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPosition);

            ((CraftPlayer) player).updateScaledHealth();

            player.updateInventory();

            ((CraftPlayer) player).getHandle().triggerHealthUpdate();

            if (fly) player.setFlying(true);

        }, 20);

    }

    @JsonIgnore
    public Property getProperty() {
        return new Property(name, value, signature);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
