package com.paulek.core.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.common.io.Config;

import java.util.Random;

public class MojangApiUtil {

    private static String PROFILE_API = "https://api.mojang.com/users/profiles/minecraft/";
    private static String SKIN_API = "https://sessionserver.mojang.com/session/minecraft/profile/";

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

        String json = Util.readWebsite(SKIN_API + uuid + "?unsigned=false");

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
        String json = Util.readWebsite(PROFILE_API + nick + "?unsigned=false");

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

}
