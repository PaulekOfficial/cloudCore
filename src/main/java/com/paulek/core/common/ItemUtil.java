package com.paulek.core.common;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {


    public static String itemstackToString(ItemStack material){
        return material.getType().data.getName();
    }

    public static List<String> itemstacksToList(List<ItemStack> list){
        List<String> materials = new ArrayList<>();
        for(ItemStack itemStack : list){
            materials.add(itemstackToString(itemStack));
        }
        return materials;
    }

    public static ItemStack itemStackFromString(String string){
        return new ItemStack(Material.getMaterial(string), 1);
    }

    public static List<ItemStack> itemStacksFromList(List<String> strings){
        List<ItemStack> itemStacks = new ArrayList<>();
        for(String string : strings){
            itemStacks.add(new ItemStack(Material.getMaterial(string), 1));
        }
        return itemStacks;
    }
}
