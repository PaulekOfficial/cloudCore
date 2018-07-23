package com.paulek.core.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.paulek.core.Core;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.objects.Skin;
import com.paulek.core.data.objects.User;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
public class Util {

    private static String url_test = "https://use.gameapis.net/mc/player/uuid/";

    public static String fixColor(String string){
        return ChatColor.translateAlternateColorCodes('$', string);
    }

    public static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean testPremium(String nick){
        String json = readWebsite(url_test + nick + "?unsigned=false");

        if(json == null) return false;

        JsonParser parser = new JsonParser();

        JsonObject object = parser.parse(json).getAsJsonObject();

        if(object.has("error")){
            return false;
        }
        return true;
    }

    public static String readWebsite(String url){

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.addRequestProperty("User-Agent", "clCore");

            httpURLConnection.setDoOutput(true);

            String line;
            StringBuilder output = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            while ((line = in.readLine()) != null)
                output.append(line);
            in.close();

            return output.toString();

        } catch (Exception e){

            e.printStackTrace();
            return null;

        }
    }

    public static void sendActionbar(Player player, String text) {

        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");

        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    public static Location randomTeleport(World world){
        Random RAND = new Random();
        int x = (Config.SETTINGS_RANDOMTELEPORT_MAX_X * -1) + RAND.nextInt(Config.SETTINGS_RANDOMTELEPORT_MAX_X -
                (Config.SETTINGS_RANDOMTELEPORT_MAX_X * -1) + 1);
        int z = (Config.SETTINGS_RANDOMTELEPORT_MAX_Z * -1) + RAND.nextInt(Config.SETTINGS_RANDOMTELEPORT_MAX_Z - (Config.SETTINGS_RANDOMTELEPORT_MAX_Z * -1) + 1);
        int y = world.getHighestBlockYAt(x, z);
        if((world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.OCEAN) || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.DEEP_OCEAN)
                || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.FROZEN_OCEAN)){
            randomTeleport(world);
        }
        Location loc = new Location(world, x, y, z);
        int yfix = world.getHighestBlockYAt(loc);
        return new Location(world, x, yfix, z);
    }

    public static void giveParticle(Player player, EnumParticle particle, Location location, int offsetX, int offsetY, int offsetZ, int speed, int amount){
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, amount);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendTitle(Player player, String messageA, String messageB, int a, int b, int c){
        IChatBaseComponent Title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + messageA + "\"}");
        IChatBaseComponent subTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + messageB + "\"}");


        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, Title);
        PacketPlayOutTitle length = new PacketPlayOutTitle(a, b, c);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitle);


        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
    }
}
