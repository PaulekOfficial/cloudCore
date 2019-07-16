package com.paulek.core.common;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class ParticlesUtil {

    public static void sendParticles18(Player player, Object enumParticle, boolean var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int... var11) throws Exception{

        Object packetPlayOutWorldParticles = NmsUtils.newInstance("PacketPlayOutWorldParticles", enumParticle, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);

        NmsUtils.sendPackets(player, packetPlayOutWorldParticles);

    }

    public static void sendParticles12(World world, Object particle, Location var2, int var3, double var4, double var6, double var8, Object var10) throws Exception{

        Method spawnParticle = NmsUtils.getNMSMethod(world.getClass(), "spawnParticle", particle.getClass(), var2.getClass(), Integer.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, var10.getClass());

        spawnParticle.invoke(world, particle, var2, var3, var4, var6, var8, var10);

    }

}
