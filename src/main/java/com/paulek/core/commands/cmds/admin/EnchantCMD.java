package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.ReflectionUtils;
import com.paulek.core.common.io.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnchantCMD extends Command {

    private boolean unsafeEnchants;

    public EnchantCMD(Core core) {
        super("enchant", "enchant a item in your hand", "/enchant {enchantment type} {lvl}", "core.cmd.enchant", new String[]{}, core);
        unsafeEnchants = core.getConfiguration().unsafeEnchantments;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if (args.length == 2) {

            Player player = (Player) sender;

            if (!getItemInHand(player.getInventory()).getType().equals(Material.AIR)) {

                int lvl;

                try {
                    lvl = Integer.valueOf(args[1]);
                } catch (Exception e) {

                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_ENCHANT_NOTNUMBER));

                    return false;
                }

                if (Enchantment.getByName(args[0].toUpperCase()) == null && !args[0].toUpperCase().equalsIgnoreCase("ALL")) {

                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_ENCHANT_NOTFOUND));

                    return false;
                }

                Enchantment enchantment = Enchantment.getByName(args[0]);

                ItemMeta itemMeta = getItemInHand(player.getInventory()).getItemMeta();

                if (enchantment.getMaxLevel() < lvl && !unsafeEnchants) {

                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_ENCHANT_UNSAFE));

                    return false;
                }

                itemMeta.addEnchant(enchantment, lvl, unsafeEnchants);

                player.getInventory().getItemInHand().setItemMeta(itemMeta);

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_ENCHANT_ENCHANTED));

            } else {

                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_ENCHANT_ITEMAIR));

                return false;
            }

        } else {

            StringBuilder enchamtment_list = new StringBuilder();

            for (Enchantment e : Enchantment.values()) {

                enchamtment_list.append(e.getName());
                enchamtment_list.append(", ");

            }

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_ENCHANT_LIST.replace("{list}", enchamtment_list.toString())));

            sender.sendMessage(getUsage());

        }

        return false;
    }

    private ItemStack getItemInHand(Inventory inventory){

        Method method = null;
        try{
            method = ReflectionUtils.getMethod(inventory.getClass(), "getItemInMainHand", null);
        } catch (NoSuchMethodException e){
            try {
                method = ReflectionUtils.getMethod(inventory.getClass(), "getItemInHand", null);
            } catch (NoSuchMethodException ex){
                ex.printStackTrace();
            }
        }

        if(method != null){
            try {
                return (ItemStack) method.invoke(inventory);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            List<String> enchantmests = new ArrayList<>();
            for(Enchantment enchantment : Enchantment.values()){
                enchantmests.add(enchantment.getName().toLowerCase());
            }
            return enchantmests;
        }

        return new ArrayList<>();
    }

}
