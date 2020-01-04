package com.paulek.core.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulek.core.Core;
import com.paulek.core.basic.skin.Skin;
import com.paulek.core.common.io.Config;

public class MojangApiUtil {

    public static Skin getPremiumSkin(String nick, Core core) {

        String uuid = getPremiumUuid(nick);

        String json = Util.readWebsite(Config.SKIN_API + uuid + ((!Config.SKIN_SIGNATURE) ? "?unsigned=false" : ""));

        if (json == null) return null;

        JsonParser parser = new JsonParser();

        try {

            JsonObject object = parser.parse(json).getAsJsonObject();
            JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();
            String name = properties.get("name").getAsString();
            String value = properties.get("value").getAsString();
            String signature = (properties.get("signature") != null) ? properties.get("signature").getAsString() : null;

            return new Skin(name, value, signature, core);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPremiumUuid(String nick) {
        String json = Util.readWebsite(Config.PROFILE_API + nick + "?unsigned=" + !Config.SKIN_SIGNATURE);

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
