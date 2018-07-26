package com.paulek.core;

import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.data.RandomtpStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.listeners.block.BlockBreakEvent;
import com.paulek.core.listeners.block.BlockPlaceEvent;
import com.paulek.core.listeners.entity.EntityDamageByEntityEvent;
import com.paulek.core.listeners.player.PlayerShearEntityEvent;
import com.paulek.core.listeners.player.*;
import com.paulek.core.managers.CombatManager;
import com.paulek.core.utils.consoleLog;
import com.paulek.core.utils.version;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Core extends JavaPlugin{

    private static Core plugin;
    private static Permission permission = null;
    private static Chat chat = null;

    @Override
    public void onEnable(){
        plugin = this;

        saveDefaultConfig();

        version version = new version();
        version.chceckVersion();

        Config.reloadConfig();
        Lang.reloadLang();

        if(!Config.ENABLED){
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        if(this.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> serviceProvider_permission = this.getServer().getServicesManager().getRegistration(Permission.class);
            if (serviceProvider_permission != null) permission = serviceProvider_permission.getProvider();
            RegisteredServiceProvider<Chat> serviceProvider_chat = this.getServer().getServicesManager().getRegistration(Chat.class);
            if(serviceProvider_chat != null) chat = serviceProvider_chat.getProvider();
            consoleLog.info("Valut detected!");
        } else {
            consoleLog.log("Warning! Valut not detected! disabling plugin...", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
        }

        registerListeners();
        registerCommands();

        if(Config.SETTINGS_COMBAT_ENABLED){
            Bukkit.getScheduler().runTaskTimer(this, new CombatManager(), 20, 20);
        }

        if(Config.ENABLE_STONEGENERATOR) {

            ItemStack item = new ItemStack(Material.ENDER_STONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Config.SETTINGS_STONEGENERATOR_NAME);
            meta.setLore(Config.SETTINGS_STONEGENERATOR_LORE);
            item.setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "iocjow98345cj9673498yjw"), item)
                    .shape(new String[]{
                    "RIR",
                    "ISI",
                    "RPR"
            })
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('I', Material.IRON_INGOT)
                    .setIngredient('S', Material.STONE)
                    .setIngredient('P', Material.PISTON_BASE);
            plugin.getServer().addRecipe(recipe);

        }

        if(Config.ENABLE_RANDOMTELEPORT) RandomtpStorage.loadButtons();

        AsyncPlayerChatEvent.loadGroups();

    }

    public static Core getPlugin() {
        return plugin;
    }

    public static String getVersion(){
        return plugin.getDescription().getVersion();
    }

    public static Chat getChat() {
        return chat;
    }

    public static Permission getPermission() {
        return permission;
    }

    public void registerListeners(){
        consoleLog.info("Registering Listeners...");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerMoveEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerTeleportEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerRespawnEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerJoinEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerQuitEvent(), getPlugin());
        pluginManager.registerEvents(new EntityDamageByEntityEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerCommandPreprocessEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerInteractEvent(), getPlugin());
        pluginManager.registerEvents(new BlockBreakEvent(), getPlugin());
        pluginManager.registerEvents(new BlockPlaceEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerShearEntityEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerDeathEvent(), getPlugin());
        pluginManager.registerEvents(new PlayerGameModeChangeEvent(), getPlugin());
    }

    public void registerCommands(){
        consoleLog.info("Registering Commands...");
        CommandManager.registerCommand(new CoreCMD());
        CommandManager.registerCommand(new ChatCMD());
        CommandManager.registerCommand(new GmCMD());
        CommandManager.registerCommand(new TpCMD());
        CommandManager.registerCommand(new FlyCMD());
        CommandManager.registerCommand(new HelpopCMD());
        CommandManager.registerCommand(new WeatherCMD());
        CommandManager.registerCommand(new TimeCMD());
        CommandManager.registerCommand(new SpawnCMD());
        CommandManager.registerCommand(new BackCMD());
        CommandManager.registerCommand(new SetSpawnCMD());
        CommandManager.registerCommand(new TpacceptCMD());
        CommandManager.registerCommand(new TpaCMD());
        CommandManager.registerCommand(new CombatCMD());
        CommandManager.registerCommand(new DayCMD());
        CommandManager.registerCommand(new NightCMD());

        if(Config.ENABLE_RANDOMTELEPORT) CommandManager.registerCommand(new RandomCMD());

        CommandManager.registerCommand(new VanishCMD());
        CommandManager.registerCommand(new MsgCMD());
        CommandManager.registerCommand(new MsgspyCMD());
        CommandManager.registerCommand(new RCMD());
        CommandManager.registerCommand(new BrodcastCMD());
        CommandManager.registerCommand(new InvseeCMD());
        CommandManager.registerCommand(new EnderchestCMD());
        CommandManager.registerCommand(new SethomeCMD());
        CommandManager.registerCommand(new HomeCMD());
        CommandManager.registerCommand(new DragondeathCMD());
        CommandManager.registerCommand(new SunCMD());
        CommandManager.registerCommand(new DelhomeCMD());
        CommandManager.registerCommand(new TpahereCMD());
        CommandManager.registerCommand(new TpadenyCMD());

        if(Config.ENABLE_SKINS) CommandManager.registerCommand(new SkinCMD());

        CommandManager.registerCommand(new SpeedCMD());
        CommandManager.registerCommand(new WorkbenchCMD());
        CommandManager.registerCommand(new AnvilCMD());
        CommandManager.registerCommand(new TphereCMD());
        CommandManager.registerCommand(new TpallCMD());
        CommandManager.registerCommand(new TptoggleCMD());
    }
}
