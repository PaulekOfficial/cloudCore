package com.paulek.core.basic.drop.mask;

import com.paulek.core.basic.data.Drops;
import com.paulek.core.basic.drop.DropMask;
import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.common.DropUtil;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockMask extends DropMask {

    @Override
    public void breakBlock(Player player, ItemStack tool, Object object) {

        Block block = (Block) object;

        List<ItemStack> toDrop = new ArrayList<>();
        for(StoneDrop drop : Drops.getDrops()){

            if(drop.canDrop(player) && drop.correctTool(tool) && drop.correctHight(Double.valueOf(block.getLocation().getBlockY())) && DropUtil.getChance(drop.getChance(player))){

                int amount = drop.getRandomAmount();
                int exp = drop.getExp();
                if(tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && drop.isFortune()){

                    amount += DropUtil.getFortuneAmount(tool);

                }

                ItemStack itemStack = drop.getMaterial().clone();
                itemStack.setAmount(amount);
                exp += exp * amount;
                toDrop.add(itemStack);
                if(drop.getMessage() != null){

                    player.sendMessage(Util.fixColor(drop.getMessage().replace("{amount}", String.valueOf(amount))));

                }

                DropUtil.dropToPlayer(toDrop, exp, player);

                if(Config.DROP_HARDCORE) break;
            }

        }

    }
}
