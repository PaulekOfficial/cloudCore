package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.NmsUtils;
import com.paulek.core.common.ParticlesUtil;
import com.paulek.core.common.XMaterial;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class StoneGeneratorListeners implements Listener {

    private Core core;

    public StoneGeneratorListeners(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) throws Exception{

        if (Config.STONEGENERATOR_ENABLE) {

            Block block = event.getBlock();
            Location loc = new Location(block.getWorld(), block.getX(), block.getY() - 1.0D, block.getZ());
            Block sb = loc.getBlock();
            if ((block.getType() == Material.STONE) && (sb.getType() == XMaterial.END_STONE.parseMaterial())) {
                sendParticles(sb.getWorld(), sb.getLocation(), event.getPlayer());
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }
            if ((block.getType() == Material.OBSIDIAN) && (sb.getType() == XMaterial.END_STONE.parseMaterial())) {
                sendParticles(sb.getWorld(), sb.getLocation(), event.getPlayer());
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }

        }
    }

    @EventHandler
    public void onBroke(org.bukkit.event.block.BlockBreakEvent event) {

        if (Config.STONEGENERATOR_ENABLE) {

            final Block block1 = event.getBlock();
            Location loc1 = block1.getLocation();
            Location stone1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() - 1.0, loc1.getZ());
            final Block sb1 = stone1.getBlock();

            final Block block2 = event.getBlock();
            Location loc2 = block1.getLocation();
            Location stone2 = new Location(loc2.getWorld(), loc2.getX(), loc2.getY() - 1.0, loc2.getZ());
            final Block sb2 = stone1.getBlock();

            if ((block1.getType() == Material.STONE) && (sb1.getType() == XMaterial.END_STONE.parseMaterial())) {

                Bukkit.getScheduler().runTaskLater(core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb1.getType() == XMaterial.END_STONE.parseMaterial()) {
                            block1.setType(Material.STONE);
                        }
                    }
                }, Config.STONEGENERATOR_REGENERATETIME * 20);

            }

            if ((block2.getType() == Material.OBSIDIAN) && (sb2.getType() == XMaterial.END_STONE.parseMaterial())) {

                Bukkit.getScheduler().runTaskLater(core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb2.getType() == XMaterial.END_STONE.parseMaterial()) {
                            block2.setType(Material.OBSIDIAN);
                        }
                    }
                }, Config.STONEGENERATOR_REGENERATETIME * 20);

            }
        }

    }

    private void sendParticles(World world, Location location, Player player) throws Exception{

        String version = core.getVersion();

        if(version.contains("1_12") || version.contains("1_13") ||version.contains("1_14")) {

            Class particleClass = NmsUtils.getBukkitClass("Particle");
            Class dustClass = NmsUtils.getBukkitClass("Particle$DustOptions");
            Field field = particleClass.getDeclaredField("dataType");
            Object particle = particleClass.getEnumConstants()[29];
            field.setAccessible(true);
            Field modifyField = Field.class.getDeclaredField("modifiers");
            modifyField.setAccessible(true);
            modifyField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(particleClass, dustClass);
            field.setAccessible(false);
            Object dustOptions = NmsUtils.newInstance(dustClass.getName(), Color.GRAY, 10);
            ParticlesUtil.sendParticles12(world, particle, location, 10, 5, 5, 5, dustOptions);
            return;

        }

        Object enumParticle = NmsUtils.getNMSClass("EnumParticle");
        Field particle = NmsUtils.getField(enumParticle, "REDSTONE");
        ParticlesUtil.sendParticles18(player, particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 5.0F, 5.0F, 5.0F, 5, 5, null);

    }

}
