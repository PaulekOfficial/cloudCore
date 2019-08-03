package com.paulek.core.common;

import com.paulek.core.Core;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {


    public static String itemstackToString(ItemStack material) {
        return material.getType().data.getName();
    }

    public static List<String> itemstacksToList(List<ItemStack> list) {
        List<String> materials = new ArrayList<>();
        for (ItemStack itemStack : list) {
            materials.add(itemstackToString(itemStack));
        }
        return materials;
    }

    public static ItemStack itemStackFromString(String string) {
        return new ItemStack(Material.getMaterial(string), 1);
    }

    public static List<ItemStack> itemStacksFromList(List<String> strings) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (String string : strings) {
            itemStacks.add(new ItemStack(Material.getMaterial(string), 1));
        }
        return itemStacks;
    }

    public static ItemStack[] deserializeItemStackList(String... strings){
        List<ItemStack> itemStacks = new ArrayList<>();

        for(String rawItemStack : strings){
            itemStacks.add(deserializeItemStack(rawItemStack, false));
        }

        return (ItemStack[]) itemStacks.toArray();
    }

    public static ItemStack deserializeItemStack(String itemStackString, boolean unSafeEnchantment){

        String[] strings = itemStackString.split(" ");

        Material material = null;
        int byteValue = 0;

        if(strings[0].contains(":")){
            String[] s = strings[0].split(":");
            material = Material.valueOf(s[0]);
            byteValue = Integer.parseInt(s[1]);
        } else {
            material = Material.valueOf(strings[0]);
        }

        int amount = 1;

        if(strings.length >= 2) {
            amount = Integer.parseInt(strings[1]);
        }

        ItemStack constructionItemStack = new ItemStack(material, amount, (short) 0, (byte) byteValue);

        int i = 2;

        while (i < strings.length){
            i++;

            String string = strings[i];

            if(string.contains("name:")){
                ItemMeta itemMeta = constructionItemStack.getItemMeta();
                itemMeta.setDisplayName(ColorUtil.fixColor(string.replace("name:", "")));
                constructionItemStack.setItemMeta(itemMeta);
            } else if(string.contains("lore:")){
                ItemMeta itemMeta = constructionItemStack.getItemMeta();
                itemMeta.setLore(ColorUtil.fixColors(deserializeItemLore(string.replace("lore:", ""))));
                constructionItemStack.setItemMeta(itemMeta);
            } else if(string.contains("durability:")) {
                constructionItemStack.setDurability(Short.parseShort(string.replace("durability:", "")));
            } else if(string.contains("potion:")){
                PotionMeta potionMeta = (PotionMeta) constructionItemStack.getItemMeta();
                PotionData potionData = new PotionData(PotionType.getByEffect(PotionEffectType.getById(Integer.parseInt(string.replace("potion:", "")))));
                potionMeta.setBasePotionData(potionData);
                constructionItemStack.setItemMeta(potionMeta);
            } else if(string.contains(":")){
                String[] rawEnchantmest = string.split(":");
                Enchantment enchantment = EnchantUtils.getEnchantment(rawEnchantmest[0]);
                if(enchantment != null){
                    if(unSafeEnchantment){
                        constructionItemStack.addUnsafeEnchantment(enchantment, Integer.parseInt(rawEnchantmest[1]));
                    }
                    constructionItemStack.addEnchantment(enchantment, Integer.parseInt(rawEnchantmest[1]));
                }
            }

        }

        return constructionItemStack;
    }

    public static String[] deserializeItemLore(String string){
        return string.split("~");
    }
}
