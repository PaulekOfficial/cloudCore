package com.paulek.core.common;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class ActionBarUtil {

    public static void sendActionbar(Player player, String text) {

        try {

            XChatBaseComponent iChatBaseComponent = new XChatBaseComponent(text);

            Object packetPlayOutChat = null;
            Class packetPlayOutChatClass = ReflectionUtils.getNMSClass("PacketPlayOutChat");
            try {

                Class chatType = ReflectionUtils.getNMSClass("ChatMessageType");
                Object gameInfo = chatType.getEnumConstants()[2];

                Field d = chatType.getDeclaredField("d");
                d.setAccessible(true);
                d.set(gameInfo, (byte)2);
                d.setAccessible(false);

                Field e = chatType.getDeclaredField("e");
                e.setAccessible(true);
                e.set(gameInfo, true);
                e.setAccessible(false);

                packetPlayOutChat = ReflectionUtils.newInstance(packetPlayOutChatClass.getName(), new Class[]{iChatBaseComponent.getiChatBaseComponentClass(), gameInfo.getClass()}, iChatBaseComponent.a(), gameInfo);

            } catch (Exception ex){

                try{
                    packetPlayOutChat = ReflectionUtils.newInstance(packetPlayOutChatClass.getName(), new Class[]{iChatBaseComponent.getiChatBaseComponentClass(), Byte.TYPE}, iChatBaseComponent.a(), (byte)2);
                } catch (Exception exc){
                    exc.printStackTrace();
                }

            }

            //PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);

            ReflectionUtils.sendPackets(player, packetPlayOutChat);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
