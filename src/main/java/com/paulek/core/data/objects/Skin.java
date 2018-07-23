package com.paulek.core.data.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import com.paulek.core.utils.Util;

public class Skin {

    String sessionserver = "https://sessionserver.mojang.com/session/minecraft/profile/";
    String api = "https://api.mojang.com/users/profiles/minecraft/";
    String gameapis = "https://use.gameapis.net/mc/player/profile/";

    private Property property;
    private String value;
    private String signature;

    public Skin(String nick){

        this.property = getProperty(nick);

    }

    public Skin(String nick, String nil){

        String uuid = this.getUUID(nick);

        if(uuid != null){
            this.property = this.getSkinProperty(uuid);
        }

    }

    public Property getProperty(String nick){
        JsonParser jsonParser = new JsonParser();

        String json = Util.readWebsite(gameapis + nick + "?unsigned=false");

        if(json == null){
            return null;
        }

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        if(jsonObject.has("error")){
            return null;
        }

        JsonArray jsonArray = jsonObject.get("properties").getAsJsonArray();

        Property property = null;

        for(int i = 0; i <= jsonArray.size(); i++){

            try {

                JsonObject object = jsonArray.get(i).getAsJsonObject();

                String name = object.get("name").getAsString();
                String value = object.get("value").getAsString();
                String signature = object.get("signature") != null ? object.get("signature").getAsString() : null;

                this.value = value;
                this.signature = signature;

                property = new Property(name, value, signature);

            } catch (Exception exe){

            }

        }
        return property;
    }

    @Deprecated
    public String getUUID(String nick){

        JsonParser jsonParser = new JsonParser();

        String json_string = Util.readWebsite(api + nick + "?unsigned=false");

        if(json_string == null){
            return null;
        }

        JsonObject jsonObject = jsonParser.parse(json_string).getAsJsonObject();

        return jsonObject.get("id").getAsString();
    }

    @Deprecated
    public Property getSkinProperty(String uuid){

        JsonParser jsonParser = new JsonParser();

        String json = Util.readWebsite(sessionserver + uuid + "?unsigned=false");

        if(json == null){
            return null;
        }

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        JsonArray jsonArray = jsonObject.get("properties").getAsJsonArray();

        Property property = null;

        for(int i = 0; i <= jsonArray.size(); i++){

            try {

                JsonObject object = jsonArray.get(i).getAsJsonObject();

                String name = object.get("name").getAsString();
                String value = object.get("value").getAsString();
                String signature = object.get("signature") != null ? object.get("signature").getAsString() : null;

                this.value = value;
                this.signature = signature;

                property = new Property(name, value, signature);

            } catch (Exception exe){

            }

        }
        return property;
    }

    public Property getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
