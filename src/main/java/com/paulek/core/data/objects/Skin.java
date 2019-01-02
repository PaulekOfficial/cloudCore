package com.paulek.core.data.objects;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.paulek.core.Core;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Skin {

    private String nick;
    private String value;
    private String signature;

    public Skin(String nick, String value, String signature){

        this.nick = nick;
        this.value = value;
        this.signature = signature;

    }

    public void applySkin(Player player){

            GameProfile gameProfile = ((CraftPlayer) player).getProfile();

            gameProfile.getProperties().clear();

            gameProfile.getProperties().put("textures", getProperty());


            for (Player p : Bukkit.getOnlinePlayers()) {

                p.hidePlayer(player);
                p.showPlayer(player);

            }

    }

    public void updateSkin(Player player){

        UUID uuid = player.getUniqueId();

        if(Bukkit.getPlayer(uuid) == null) return;

        EnumGamemode enumGamemode = EnumGamemode.getById(player.getGameMode().getValue());

        WorldType worldType = WorldType.getType(player.getWorld().getWorldType().getName());

        EnumDifficulty enumDifficulty = EnumDifficulty.getById(player.getWorld().getDifficulty().getValue());

        DimensionManager dimension;

        switch (player.getWorld().getEnvironment()){
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

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_remove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle());

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle());

        PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(dimension, enumDifficulty, worldType, enumGamemode);

        Location loc = player.getLocation();

        PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<>(), 0);

        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), () -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo_remove);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo_add);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutRespawn);

            if(player.isFlying()) player.setFlying(true);

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutPosition);

            ((CraftPlayer) player).updateScaledHealth();

            player.updateInventory();

            ((CraftPlayer) player).getHandle().triggerHealthUpdate();

        }, 20);

    }

    public Property getProperty(){
        return new Property(nick, value, signature);
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
