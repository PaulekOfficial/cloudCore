package com.paulek.core.basic.skin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.paulek.core.Core;
import com.paulek.core.common.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

public class Skin implements SkinBase {

    private String name;
    private String value;
    private String signature;
    private LocalDateTime lastUpdate;
    private Core core;
    private boolean dirty;


    public Skin(String nick, String value, String signature, LocalDateTime lastUpdate, Core core) {

        this.name = nick;
        this.value = value;
        this.signature = signature;
        this.lastUpdate = lastUpdate;
        this.core = Objects.requireNonNull(core, "Core");
        this.dirty = false;

    }

    public Skin(String name, String value, String signature, Core core) {

        this.name = name;
        this.value = value;
        this.signature = signature;
        this.lastUpdate = LocalDateTime.now();
        this.core = Objects.requireNonNull(core, "Core");

    }

    public void applySkinForPlayers(Player player) {

        try {

            Object craftPlayer = ReflectionUtils.getNMSPlayer(player);
            Method getProfile = ReflectionUtils.getMethod(craftPlayer.getClass(), "getProfile");

            GameProfile gameProfile = (GameProfile) getProfile.invoke(craftPlayer);

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

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateSkinForPlayer(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {

                    String version = ReflectionUtils.getNMSVersion();

                    Object craftPlayer = ReflectionUtils.getNMSPlayer(player);

                    Class worldTypeClass = ReflectionUtils.getNMSClass("WorldType");
                    Class packetPlayOutPlayerInfoClass = ReflectionUtils.getNMSClass("PacketPlayOutPlayerInfo");
                    Class enumPlayerInfoAction = ReflectionUtils.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
                    Class packetPlayOutRespawnClass = ReflectionUtils.getNMSClass("PacketPlayOutRespawn");
                    Class packetPlayOutPositionClass = ReflectionUtils.getNMSClass("PacketPlayOutPosition");
                    Class craftItemStackClass = ReflectionUtils.getCraftBukkitClass("inventory.CraftItemStack");
                    Class playOutEntityEquipmentClass = ReflectionUtils.getNMSClass("PacketPlayOutEntityEquipment");
                    Class itemStackClass = ReflectionUtils.getNMSClass("ItemStack");

                    Method getType = ReflectionUtils.getMethod(worldTypeClass, "getType", String.class);
                    Method asNMSCopy = ReflectionUtils.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);

                    Object dimension;
                    if(version.contains("1_13") || version.contains("1_14")) {
                        Class dimensionManagerClass = ReflectionUtils.getNMSClass("DimensionManager");
                        Method a = ReflectionUtils.getMethod(dimensionManagerClass, "a", Integer.TYPE);

                        switch (player.getWorld().getEnvironment()) {
                            case THE_END:
                                dimension = a.invoke(null, 1);
                                break;
                            case NETHER:
                                dimension = a.invoke(null, -1);
                                break;
                            default:
                                dimension = a.invoke(null, 0);
                                break;
                        }

                    } else {
                        switch (player.getWorld().getEnvironment()) {
                            case THE_END:
                                dimension = 1;
                                break;
                            case NETHER:
                                dimension = -1;
                                break;
                            default:
                                dimension = 0;
                                break;
                        }
                    }

                    Object enumGamemode;

                    if(version.contains("1_13") || version.contains("1_14")) {
                        Class enumGamemodeClass = ReflectionUtils.getNMSClass("EnumGamemode");
                        Method getById = ReflectionUtils.getMethod(enumGamemodeClass, "getById", Integer.TYPE);

                        enumGamemode = getById.invoke(null, player.getGameMode().getValue());
                    } else {
                        Class enumGamemodeClass = ReflectionUtils.getNMSClass("WorldSettings$EnumGamemode");
                        Method getById = ReflectionUtils.getMethod(enumGamemodeClass, "getById", Integer.TYPE);

                        enumGamemode = getById.invoke(null, player.getGameMode().getValue());
                    }

                    Object worldType = getType.invoke(null, player.getWorld().getWorldType().getName());
                    Object removePlayerEnum = enumPlayerInfoAction.getEnumConstants()[4];
                    Object addPlayerEnum = enumPlayerInfoAction.getEnumConstants()[0];

                    List<Object> players = new ArrayList<>();
                    players.add(craftPlayer);
                    Iterable<?> iterablePlayers = players;

                    Object packetPlayOutPlayerInfo_remove = ReflectionUtils.newInstance(packetPlayOutPlayerInfoClass.getName(), new Class<?>[]{enumPlayerInfoAction, Iterable.class}, removePlayerEnum, iterablePlayers);
                    Object packetPlayOutPlayerInfo_add = ReflectionUtils.newInstance(packetPlayOutPlayerInfoClass.getName(), new Class<?>[]{enumPlayerInfoAction, Iterable.class}, addPlayerEnum, iterablePlayers);

                    Object packetPlayOutRespawn;
                    if(version.contains("1_13") || version.contains("1_14")) {
                        Class dimensionManagerClass = ReflectionUtils.getNMSClass("DimensionManager");
                        Class enumGamemodeClass = ReflectionUtils.getNMSClass("EnumGamemode");
                        packetPlayOutRespawn = ReflectionUtils.newInstance(packetPlayOutRespawnClass.getName(), new Class<?>[]{dimensionManagerClass, worldTypeClass, enumGamemodeClass}, dimension, worldType, enumGamemode);
                    } else {
                        Class enumDifficultyClass = ReflectionUtils.getNMSClass("EnumDifficulty");
                        Method getDifficultyById = ReflectionUtils.getMethod(enumDifficultyClass, "getById", Integer.TYPE);
                        Object enumDifficulty = getDifficultyById.invoke(null, player.getWorld().getDifficulty().getValue());
                        Class enumGamemodeClass = ReflectionUtils.getNMSClass("WorldSettings$EnumGamemode");

                        packetPlayOutRespawn = ReflectionUtils.newInstance(packetPlayOutRespawnClass.getName(), new Class<?>[]{Integer.TYPE, enumDifficultyClass, worldTypeClass, enumGamemodeClass}, dimension, enumDifficulty, worldType, enumGamemode);
                    }

                    Location loc = player.getLocation();

                    Object packetPlayOutPosition;
                    if(version.contains("1_13") || version.contains("1_14")) {
                        packetPlayOutPosition = ReflectionUtils.newInstance(packetPlayOutPositionClass.getName(), new Class<?>[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Set.class, Integer.TYPE}, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<>(), 0);
                    } else {
                        packetPlayOutPosition = ReflectionUtils.newInstance(packetPlayOutPositionClass.getName(), new Class<?>[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Set.class}, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<>());
                    }

                    List<Object> hands = new ArrayList<>();
                    Object playOutEntityEquipmentHelmet = null;
                    Object playOutEntityEquipmentChestplate = null;
                    Object playOutEntityEquipmentLeggings = null;
                    Object playOutEntityEquipmentBoots = null;

                    PlayerInventory playerInventory = player.getInventory();
                    Vector vector = player.getVelocity();

                    if (version.contains("1_8") || version.contains("1_9")) {

                        hands.add(ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, Integer.TYPE, itemStackClass}, player.getEntityId(), 0, asNMSCopy.invoke(null, playerInventory.getItemInHand())));

                        playOutEntityEquipmentHelmet = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, Integer.TYPE, itemStackClass}, player.getEntityId(), 4, asNMSCopy.invoke(null, playerInventory.getHelmet()));

                        playOutEntityEquipmentChestplate = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, Integer.TYPE, itemStackClass}, player.getEntityId(), 3, asNMSCopy.invoke(null, playerInventory.getChestplate()));

                        playOutEntityEquipmentLeggings = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, Integer.TYPE, itemStackClass}, player.getEntityId(), 2, asNMSCopy.invoke(null, playerInventory.getLeggings()));

                        playOutEntityEquipmentBoots = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, Integer.TYPE, itemStackClass}, player.getEntityId(), 1, asNMSCopy.invoke(null, playerInventory.getBoots()));

                    } else {

                        Class enumItemSlotClass = ReflectionUtils.getNMSClass("EnumItemSlot");

                        hands.add(ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[0], asNMSCopy.invoke(null, playerInventory.getItemInMainHand())));
                        hands.add(ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[1], asNMSCopy.invoke(null, playerInventory.getItemInOffHand())));

                        playOutEntityEquipmentHelmet = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[5], asNMSCopy.invoke(null, playerInventory.getHelmet()));

                        playOutEntityEquipmentChestplate = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[4], asNMSCopy.invoke(null, playerInventory.getChestplate()));

                        playOutEntityEquipmentLeggings = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[3], asNMSCopy.invoke(null, playerInventory.getLeggings()));

                        playOutEntityEquipmentBoots = ReflectionUtils.newInstance(playOutEntityEquipmentClass.getName(), new Class<?>[]{Integer.TYPE, enumItemSlotClass, itemStackClass}, player.getEntityId(), enumItemSlotClass.getEnumConstants()[2], asNMSCopy.invoke(null, playerInventory.getBoots()));

                    }

                    boolean fly = player.isFlying();

                    ReflectionUtils.sendPackets(player, packetPlayOutPlayerInfo_remove);
                    ReflectionUtils.sendPackets(player, packetPlayOutPlayerInfo_add);
                    ReflectionUtils.sendPackets(player, packetPlayOutRespawn);

                    ReflectionUtils.sendPackets(player, packetPlayOutPosition);

//                    if(version.contains("1_13") || version.contains("1_14")) {
//                        Method updateScaledHealth = ReflectionUtils.getMethod(craftPlayer.getClass(), "updateScaledHealth");
//                        updateScaledHealth.invoke(craftPlayer);
//                    }

                    for (Object hand : hands) {
                        ReflectionUtils.sendPackets(player, hand);
                    }

                    ReflectionUtils.sendPackets(player, playOutEntityEquipmentHelmet);
                    ReflectionUtils.sendPackets(player, playOutEntityEquipmentChestplate);
                    ReflectionUtils.sendPackets(player, playOutEntityEquipmentLeggings);
                    ReflectionUtils.sendPackets(player, playOutEntityEquipmentBoots);

                    player.updateInventory();
                    player.setVelocity(vector);
                    player.setFlying(fly);

                    if(version.contains("1_13") || version.contains("1_14")) {
                        Method triggerHealthUpdate = ReflectionUtils.getMethod(craftPlayer.getClass(), "triggerHealthUpdate");
                        triggerHealthUpdate.invoke(craftPlayer);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
