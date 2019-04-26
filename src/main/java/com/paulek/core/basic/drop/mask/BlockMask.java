package com.paulek.core.basic.drop.mask;

import com.paulek.core.Core;
import com.paulek.core.basic.UserSettings;
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
import java.util.Objects;

public class BlockMask extends DropMask {

    private Core core;

    public BlockMask(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    @Override
    public void breakBlock(Player player, ItemStack tool, Object object) {

        Block block = (Block) object;

        UserSettings userSettings = core.getUsersStorage().getUser(player.getUniqueId()).getSettings();

        setDropped(false);

        List<ItemStack> toDrop = new ArrayList<>();
        for (StoneDrop drop : core.getDrops().getDrops()) {

            if (drop.canDrop(player) && drop.correctTool(tool) && drop.correctHight(block.getLocation().getY()) && DropUtil.getChance(drop.getChance(player)) && userSettings.canDrop(drop.getName())) {

                int amount = drop.getRandomAmount();
                int exp = drop.getExp();
                if (tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && drop.isFortune()) {

                    amount += DropUtil.getFortuneAmount(tool);

                }

                ItemStack itemStack = drop.getMaterial().clone();
                itemStack.setAmount(amount);
                exp += exp * amount;
                toDrop.add(itemStack);
                if (drop.getMessage() != null) {

                    player.sendMessage(Util.fixColor(drop.getMessage().replace("{amount}", String.valueOf(amount))));

                }

                DropUtil.drop(toDrop, exp, player, block.getLocation());

                setDropped(true);

                if (Config.DROP_HARDCORE) break;
            }

        }

    }
}
