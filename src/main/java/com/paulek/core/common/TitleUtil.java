package com.paulek.core.common;

import org.bukkit.entity.Player;

public class TitleUtil {

    public static void sendTitle(Player player, String messageA, String messageB, int a, int b, int c) {
        try {
            XChatBaseComponent title = new XChatBaseComponent(messageA);
            XChatBaseComponent subtitle = new XChatBaseComponent(messageB);

            Class packetPlayOutTitle = ReflectionUtils.getNMSClass("PacketPlayOutTitle");
            Class enumTitleAction = ReflectionUtils.getNMSClass("PacketPlayOutTitle$EnumTitleAction");

            Object titlePacket = ReflectionUtils.newInstance(packetPlayOutTitle.getName(), new Class[]{enumTitleAction, title.getiChatBaseComponentClass()}, enumTitleAction.getEnumConstants()[0], title.a());
            Object lenghtPacket = ReflectionUtils.newInstance(packetPlayOutTitle.getName(), new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}, a, b, c);
            Object subtitlePacket = ReflectionUtils.newInstance(packetPlayOutTitle.getName(), new Class[]{enumTitleAction, title.getiChatBaseComponentClass()}, enumTitleAction.getEnumConstants()[1], subtitle.a());


            ReflectionUtils.sendPackets(player, titlePacket);
            ReflectionUtils.sendPackets(player, lenghtPacket);
            ReflectionUtils.sendPackets(player, subtitlePacket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
