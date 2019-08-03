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

    private Map<String, Kit> kits;
    private File file;
    private YamlConfiguration fileConfiguration;
    private Core core;

    public Kits(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        file = new File(core.getPlugin().getDataFolder(), "kits.yml");
        kits = new HashMap<>();
    }

    public void reload() {
        kits.clear();
        init();
    }

    private void create() {
        core.getConsoleLog().info("Initializing kits...");
        if (!file.exists()) {
            file.getParentFile().mkdir();
            core.getPlugin().saveResource("kits.yml", true);
        }
    }

    public void init() {
        this.create();
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        core.getConsoleLog().info("Loading kits...");

        List<Kit> kitList = new ArrayList<>();
        kitList.addAll((Collection<? extends Kit>) fileConfiguration.getList("kits"));

        for(Kit kit : kitList){
            kits.put(kit.getName(), kit);
        }

        core.getConsoleLog().info("Loaded kits!");

    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}
