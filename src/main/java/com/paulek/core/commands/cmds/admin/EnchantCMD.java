package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCMD extends Command {

    private boolean unsafeEnchants = Config.SETTINGS_ENCHANT_UNSAFE;

    public EnchantCMD(){
        super("enchant", "enchant a item in your hand", "/enchant {enchantment type} {lvl}", "core.enchant", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        if(args.length == 2) {

            Player player = (Player) sender;

            if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {

                int lvl;

                try {
                    lvl = Integer.valueOf(args[1]);
                } catch (Exception e) {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_ENCHANT_NOTNUMBER));

                    return false;
                }

                if (Enchantment.getByName(args[0]) == null) {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_ENCHANT_NOTFOUND));

                    return false;
                }

                Enchantment enchantment = Enchantment.getByName(args[0]);

                ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

                if (enchantment.getMaxLevel() < lvl && !unsafeEnchants) {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_ENCHANT_UNSAFE));

                    return false;
                }

                itemMeta.addEnchant(enchantment, lvl, unsafeEnchants);

                player.getInventory().getItemInMainHand().setItemMeta(itemMeta);

                sender.sendMessage(Util.fixColor(Lang.INFO_ENCHANT_ENCHANTED));

            } else {

                sender.sendMessage(Util.fixColor(Lang.ERROR_ENCHANT_ITEMAIR));

                return false;
            }

        } else {

            StringBuilder enchamtment_list = new StringBuilder();

            for(Enchantment e : Enchantment.values()){

                enchamtment_list.append(e.getName());
                enchamtment_list.append(", ");

            }

            sender.sendMessage(Util.fixColor(Lang.INFO_ENCHANT_LIST.replace("{list}", enchamtment_list.toString())));

            sender.sendMessage(getUsage());

        }

        return false;
    }
}
