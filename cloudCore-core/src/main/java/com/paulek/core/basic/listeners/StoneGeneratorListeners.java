package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import common.ColorUtil;
import common.ParticlesUtil;
import common.ReflectionUtils;
import common.XMaterial;
import common.io.Lang;
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
        Block block = event.getBlock();
        Location loc = new Location(block.getWorld(), block.getX(), block.getY() - 1.0D, block.getZ());
        Block sb = loc.getBlock();
        if(core.getConfiguration().generatorStone && !core.getConfiguration().generatorOverride) {
            if ((block.getType() == Material.STONE) && (sb.getType() == XMaterial.END_STONE.parseMaterial())) {
//                sendParticles(sb.getWorld(), sb.getLocation(), event.getPlayer());
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }
        }
        if(core.getConfiguration().generatorObsidian && !core.getConfiguration().generatorOverride) {
            if ((block.getType() == Material.OBSIDIAN) && (sb.getType() == XMaterial.END_STONE.parseMaterial())) {
//                sendParticles(sb.getWorld(), sb.getLocation(), event.getPlayer());
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.INFO_STONEGENERATOR_PLACE));
            }
        }
        if(core.getConfiguration().generatorOverride){
            if(block.getType().equals(Material.getMaterial(core.getConfiguration().generatorBlock))){
                loc.setY(loc.getY() + 2.0D);
                loc.getBlock().setType(Material.STONE);
            }
        }
    }

    @EventHandler
    public void onBroke(org.bukkit.event.block.BlockBreakEvent event) {

        final Block block1 = event.getBlock();
        Location loc1 = block1.getLocation();
        Location stone1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() - 1.0, loc1.getZ());
        final Block sb1 = stone1.getBlock();

        final Block block2 = event.getBlock();
        Location loc2 = block1.getLocation();
        Location stone2 = new Location(loc2.getWorld(), loc2.getX(), loc2.getY() - 1.0, loc2.getZ());
        final Block sb2 = stone1.getBlock();

        if(core.getConfiguration().generatorStone || core.getConfiguration().generatorOverride) {

            if ((block1.getType() == Material.STONE) && (sb1.getType() == XMaterial.END_STONE.parseMaterial())) {

                Bukkit.getScheduler().runTaskLater(core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb1.getType() == XMaterial.END_STONE.parseMaterial()) {
                            block1.setType(Material.STONE);
                        }
                    }
                }, core.getConfiguration().generatorDelay * 20);

            }

        }

        if(core.getConfiguration().generatorObsidian && !core.getConfiguration().generatorOverride) {

            if ((block2.getType() == Material.OBSIDIAN) && (sb2.getType() == XMaterial.END_STONE.parseMaterial())) {

                Bukkit.getScheduler().runTaskLater(core.getPlugin(), new Runnable() {
                    public void run() {
                        if (sb2.getType() == XMaterial.END_STONE.parseMaterial()) {
                            block2.setType(Material.OBSIDIAN);
                        }
                    }
                }, core.getConfiguration().generatorDelay * 20);

            }

        }

    }

    private void sendParticles(World world, Location location, Player player) throws Exception{

        String version = core.getVersion();

        if(version.contains("1_12") || version.contains("1_13") ||version.contains("1_14")) {

            Class particleClass = ReflectionUtils.getBukkitClass("Particle");
            Class dustClass = ReflectionUtils.getBukkitClass("Particle$DustOptions");
            Field field = particleClass.getDeclaredField("dataType");
            Object particle = particleClass.getEnumConstants()[29];
            field.setAccessible(true);

            Field modifyField = Field.class.getDeclaredField("modifiers");
            modifyField.setAccessible(true);
            modifyField.set(field, field.getModifiers() & ~Modifier.FINAL);
            modifyField.setAccessible(false);

            field.setAccessible(true);
            field.set(particleClass, dustClass);

            field.setAccessible(false);
            Object dustOptions = ReflectionUtils.newInstance(dustClass.getName(), Color.GRAY, 10);
            ParticlesUtil.sendParticles12(world, particle, location, 10, 5, 5, 5, dustOptions);
            return;
        }

        Object enumParticle = ReflectionUtils.getNMSClass("EnumParticle");
        Field particle = ReflectionUtils.getField(enumParticle, "REDSTONE");
        ParticlesUtil.sendParticles18(player, particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 5.0F, 5.0F, 5.0F, 5, 5, null);

    }

}
