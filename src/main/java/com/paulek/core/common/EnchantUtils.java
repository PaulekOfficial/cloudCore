package com.paulek.core.common;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Method;

public class EnchantUtils {

    public static Enchantment getEnchantment(String name){
        try{

            Class namespaceKey = NmsUtils.getBukkitClass("NamespacedKey");
            Method minecraft = NmsUtils.getNMSMethod(namespaceKey, "minecraft", String.class);
            Object key = minecraft.invoke(namespaceKey, name);

            Method getByKey = NmsUtils.getNMSMethod(Enchantment.class, "getByKey", namespaceKey);
            Enchantment enchantment = (Enchantment) getByKey.invoke(Enchantment.class, key);

            if(enchantment != null){
                return enchantment;
            }
        } catch (Exception e){
            try {
                Method getByName = NmsUtils.getNMSMethod(Enchantment.class, "getByName", String.class);
                Enchantment enchantment = (Enchantment) getByName.invoke(Enchantment.class, name);

                if(enchantment != null){
                    return enchantment;
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return null;
    }

}
