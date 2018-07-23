package com.paulek.core.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.paulek.core.Core;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.objects.Skin;
import com.paulek.core.data.objects.User;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class SkinUtil {

    public static void applySkin(Player player, Skin skin){

        if(skin != null){

            User user = UserStorage.getUser(player.getUniqueId());

            GameProfile gameProfile = ((CraftPlayer) player).getProfile();

            gameProfile.getProperties().clear();

            gameProfile.getProperties().put("textures", skin.getProperty());

            updateSkin(player);

            for (Player p : Bukkit.getOnlinePlayers()) {

                p.hidePlayer(player);
                p.showPlayer(player);

            }

        }

    }

    public static void updateSkin(Player player_update){

        UUID uuid = player_update.getUniqueId();

        if(Bukkit.getPlayer(uuid) == null) return;

        Player player = Bukkit.getPlayer(uuid);

        User user = UserStorage.getUser(player.getUniqueId());

        EnumGamemode enumGamemode = EnumGamemode.getById(user.getGamemode());

        WorldType worldType = WorldType.getType(player.getWorld().getWorldType().getName());

        EnumDifficulty enumDifficulty = EnumDifficulty.getById(player.getWorld().getDifficulty().getValue());

        int dimension = player.getWorld().getEnvironment().getId();

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_remove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle());

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo_add = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle());

        PacketPlayOutRespawn packetPlayOutRespawn = new PacketPlayOutRespawn(dimension, enumDifficulty, worldType, enumGamemode);

        Location loc = player.getLocation();

        PacketPlayOutPosition packetPlayOutPosition = new PacketPlayOutPosition(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<PacketPlayOutPosition.EnumPlayerTeleportFlags>(), 0);

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

    public static void applySkin(Player player){

        User user = UserStorage.getUser(player.getUniqueId());

        if((user.getSkin() != null && !user.getSkin().equalsIgnoreCase(" ")) || user.isSkinset_manually()){

            if((!(user.getSkin_senility() <= System.currentTimeMillis())) || user.isSkinset_manually()) {

                GameProfile gameProfile = ((CraftPlayer) player).getProfile();

                gameProfile.getProperties().clear();

                gameProfile.getProperties().put("textures", new Property("textures", user.getSkin(), user.getSignature()));

                updateSkin(player);

                for (Player p : Bukkit.getOnlinePlayers()) {

                    p.hidePlayer(player);
                    p.showPlayer(player);

                }
                return;
            }
        }

        if(!Util.testPremium(player.getDisplayName())){

            if(Config.SETTINGS_SKINS_ENABLENOPREMIUMRANDOMSKIN) {

                if(Config.SETTINGS_NONPREMIUMSKINS.size() == 0) return;

                Random random = new Random();

                int rand = random.nextInt(Config.SETTINGS_NONPREMIUMSKINS.size());

                String nick = Config.SETTINGS_NONPREMIUMSKINS.get(rand);

                Skin skin = new Skin(nick, null);

                if (skin.getProperty() != null) {
                    GameProfile gameProfile = ((CraftPlayer) player).getProfile();

                    gameProfile.getProperties().clear();

                    gameProfile.getProperties().put("textures", skin.getProperty());

                    updateSkin(player);

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        p.hidePlayer(player);
                        p.showPlayer(player);

                    }

                    user.setSkin(skin.getValue());
                    user.setSignature(skin.getSignature());
                    user.setSkin_senility(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);

                }
            }

            return;
        }

        Skin skin = new Skin(player.getDisplayName(), null);

        if(skin.getProperty() != null){
            GameProfile gameProfile = ((CraftPlayer)player).getProfile();

            gameProfile.getProperties().clear();

            gameProfile.getProperties().put("textures", skin.getProperty());

            updateSkin(player);

            for(Player p : Bukkit.getOnlinePlayers()){

                p.hidePlayer(player);
                p.showPlayer(player);

            }

            user.setSkin(skin.getValue());
            user.setSignature(skin.getSignature());
            user.setSkin_senility(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);

        }

    }

}
