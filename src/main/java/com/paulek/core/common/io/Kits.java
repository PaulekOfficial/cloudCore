package com.paulek.core.common.io;

import com.paulek.core.Core;
import com.paulek.core.basic.Kit;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.EnchantUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;


//TODO Rewrite tej calej funkcji, chujowe to
public class Kits {

    private Map<String, Kit> kits = new HashMap<>();
    private File file;
    private YamlConfiguration fileConfiguration;
    private Core core;

    public Kits(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        file = new File(core.getPlugin().getDataFolder(), "kits.yml");
        create();
        load();
    }

    public void reload() {
        kits = new HashMap<>();
        create();
        load();
    }

    private void create() {
        core.getConsoleLog().info("Initializing kits...");
        if (!file.exists()) {
            file.getParentFile().mkdir();
            core.getPlugin().saveResource("kits.yml", true);
        }
    }

    private void load() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection cs = fileConfiguration.getConfigurationSection("kits");

        for (String kit : cs.getKeys(false)) {

            ConfigurationSection kitConfiguration = cs.getConfigurationSection(kit);

            String name = ColorUtil.fixColor(kitConfiguration.getString("menu-name"));
            String[] material = kitConfiguration.getString("gui-item").split(":");
            ItemStack guiItem = new ItemStack(Material.getMaterial(material[0]), 1);
            if (material.length == 2)
                guiItem = new ItemStack(Material.getMaterial(material[0]), 1, Short.valueOf(material[1]));
            ItemMeta im = guiItem.getItemMeta();
            im.setDisplayName(ColorUtil.fixColor(name));
            guiItem.setItemMeta(im);
            String permission = kitConfiguration.getString("permission");
            String description = ColorUtil.fixColor(kitConfiguration.getString("description"));
            int cooldown = kitConfiguration.getInt("cooldown");
            boolean showInGui = kitConfiguration.getBoolean("show-in-gui");
            List<ItemStack> content = new ArrayList<>();

            for (String item : kitConfiguration.getStringList("content")) {

                String itemName = "";
                int amount = 1;
                Map<Enchantment, Integer> enchants = new HashMap<>();
                List<String> lore = new ArrayList<>();
                Material itemMaterial;
                short durability = 0;

                String[] items = item.split(" ");

                String[] materials = items[0].split(":");

                itemMaterial = Material.getMaterial(materials[0]);

                if (item.length() >= 2) amount = Integer.valueOf(items[1]);


                for (String s : items) {

                    boolean elementRead = false;

                    if (s.contains("name:") && !elementRead) {
                        String kitName = s.replace("name:", "");
                        itemName = ColorUtil.fixColor(kitName.replace("_", " "));
                        elementRead = true;
                    }

                    if (s.contains("lore:") && !elementRead) {
                        String[] kitLore = s.replace("lore:", "").split("~");
                        for (String klore : kitLore) {
                            lore.add(ColorUtil.fixColor(klore.replace("_", " ")));
                        }
                        elementRead = true;
                    }

                    if (s.contains("durability:") && !elementRead) {
                        durability = Short.valueOf(s.replace("durability:", ""));
                        elementRead = true;
                    }



                    if (s.contains(":") && !elementRead) {
                        String[] enchantment = s.split(":");
                        Enchantment e = EnchantUtils.getEnchantment(enchantment[0].toLowerCase());
                        if (e != null) enchants.put(e, Integer.valueOf(enchantment[1]));
                    }

                }

                ItemStack is = new ItemStack(itemMaterial, amount);
                if (materials.length == 2)
                    is = new ItemStack(Material.getMaterial(materials[0]), amount, Short.valueOf(materials[1]));
                is.setDurability(durability);
                is.addUnsafeEnchantments(enchants);
                ItemMeta m = is.getItemMeta();
                m.setDisplayName(itemName);
                m.setLore(lore);
                is.setItemMeta(m);
                content.add(is);
            }

            kits.put(kit, new Kit(name, permission, guiItem, content, true, showInGui, cooldown, description));
            core.getConsoleLog().info("Loaded kit: " + ColorUtil.fixColor(name));

        }

    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}
