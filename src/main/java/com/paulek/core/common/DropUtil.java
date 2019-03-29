package com.paulek.core.common;

import com.paulek.core.common.io.Config;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DropUtil {

    private static Random random = new Random();

    public static boolean getChance(Double chance) {
        return chance >= 100.0 || chance >= getRandomDouble(0.0, 100.0);
    }

    public static double getRandomDouble(double min, double max) {
        return random.nextDouble() * (max - min + 1D) + min;
    }

    public static int getRandomInteger(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int getFortuneAmount(ItemStack itemStack) {

        switch (itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) {
            case 1:
                if (getChance(Config.DROP_FORTUNE_1_CHANCE))
                    return getRandomAmountFromString(Config.DROP_FORTUNE_1_AMOUNT);
                break;
            case 2:
                if (getChance(Config.DROP_FORTUNE_2_CHANCE))
                    return getRandomAmountFromString(Config.DROP_FORTUNE_2_AMOUNT);
                break;
            case 3:
                if (getChance(Config.DROP_FORTUNE_3_CHANCE))
                    return getRandomAmountFromString(Config.DROP_FORTUNE_3_AMOUNT);
                break;

        }

        return 0;
    }

    public static int getRandomAmountFromString(String amount) {
        String[] strings = amount.split("-");
        return getRandomInteger(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
    }

    public static void dropToPlayer(List<ItemStack> items, int exp, Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();
        for (ItemStack item : items) {

            world.dropItemNaturally(location, item);

        }
        player.setExp(player.getExp() + exp);

    }

}
