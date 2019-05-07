package com.paulek.core.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.common.io.Config;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

    private static String PROFILE_API = "https://api.mojang.com/users/profiles/minecraft/";
    private static String SKIN_API = "https://sessionserver.mojang.com/session/minecraft/profile/";

    public static String fixColor(String string) {
        return ChatColor.translateAlternateColorCodes('$', string);
    }

    public static List<String> fixColors(List<String> string) {
        List<String> arrayList = new ArrayList<>();
        for (String line : string) {
            arrayList.add(ChatColor.translateAlternateColorCodes('$', line));
        }
        return arrayList;
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

    public static Skin getPremiumSkin(String nick, Core core) {

        String uuid = getPremiumUuid(nick);

        if (Config.SKINS_HIDENONPREMIUM) {

            if (uuid == null) {

                Random random = new Random();

                String randomPremiumNick = Config.SKINS_SKINSFORNONPREMIUM.get(random.nextInt(Config.SKINS_SKINSFORNONPREMIUM.size()));

                uuid = getPremiumUuid(randomPremiumNick);

            }

        } else if (uuid == null) {
            return null;
        }

        String json = readWebsite(SKIN_API + uuid + "?unsigned=false");

        if (json == null) return null;

        JsonParser parser = new JsonParser();

        try {

            JsonObject object = parser.parse(json).getAsJsonObject();
            JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();
            String name = properties.get("name").getAsString();
            String value = properties.get("value").getAsString();
            String signature = properties.get("signature").getAsString();

            return new Skin(name, value, signature, core);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPremiumUuid(String nick) {
        String json = readWebsite(PROFILE_API + nick + "?unsigned=false");

        if (json == null) return null;

        JsonParser parser = new JsonParser();

        try {

            JsonObject object = parser.parse(json).getAsJsonObject();

            if (object.has("error")) {
                return null;
            }

            return object.get("id").getAsString();

        } catch (Exception e) {
            return null;
        }
    }

    public static String readWebsite(String url) {

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.addRequestProperty("User-Agent", "clCore");

            httpURLConnection.setConnectTimeout(5000);

            httpURLConnection.setDoOutput(true);

            String line;
            StringBuilder output = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            while ((line = in.readLine()) != null)
                output.append(line);
            in.close();

            return output.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }

    public static void sendActionbar(Player player, String text) {

        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");

        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    public static Location randomTeleport(World world) {
        Random RAND = new Random();
        int x = (Config.RTP_MAXVALUES_X * -1) + RAND.nextInt(Config.RTP_MAXVALUES_X -
                (Config.RTP_MAXVALUES_X * -1) + 1);
        int z = (Config.RTP_MAXVALUES_Z * -1) + RAND.nextInt(Config.RTP_MAXVALUES_Z - (Config.RTP_MAXVALUES_Z * -1) + 1);
        int y = world.getHighestBlockYAt(x, z);
        if ((world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.OCEAN) || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.DEEP_OCEAN)
                || (world.getBlockAt(new Location(world, x, y, z)).getBiome() == Biome.FROZEN_OCEAN)) {
            randomTeleport(world);
        }
        Location loc = new Location(world, x, y, z);
        int yfix = world.getHighestBlockYAt(loc);
        return new Location(world, x, yfix, z);
    }

    public static void sendTitle(Player player, String messageA, String messageB, int a, int b, int c) {
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
