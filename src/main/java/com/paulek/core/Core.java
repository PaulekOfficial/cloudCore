package com.paulek.core;

import com.paulek.core.basic.CombatManager;
import com.paulek.core.basic.MySQL;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.*;
import com.paulek.core.basic.listeners.*;
import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.common.consoleLog;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Kits;
import com.paulek.core.common.io.Lang;
import com.paulek.core.common.version;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class Core extends JavaPlugin{

    private Plugin plugin;
    private Permission permission = null;
    private Chat chat = null;
    private MySQL mysql;

    private CombatStorage combatStorage;
    private Drops dropsStorage;
    private Pms pmsStorage;
    private Rtps rtpsStorage;
    private TpaStorage tpaStorage;
    private Users usersStorage;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        version version = new version();
        version.chceckVersion();

        Config.reloadConfig();
        Lang.reloadLang();

        if (!Config.ENABLED) {
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        new Kits();

        CombatManager combatManager = new CombatManager(this);

        //init all storages
        combatStorage = new CombatStorage();
        dropsStorage = new Drops();
        pmsStorage = new Pms();
        rtpsStorage = new Rtps();
        tpaStorage = new TpaStorage();
        usersStorage = new Users(this);

        //Valut permissions initialization
        if (this.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> serviceProvider_permission = this.getServer().getServicesManager().getRegistration(Permission.class);
            if (serviceProvider_permission != null) permission = serviceProvider_permission.getProvider();
            RegisteredServiceProvider<Chat> serviceProvider_chat = this.getServer().getServicesManager().getRegistration(Chat.class);
            if (serviceProvider_chat != null) chat = serviceProvider_chat.getProvider();
            consoleLog.info("Valut detected!");
        } else {
            consoleLog.log("Warning! Valut not detected! disabling plugin...", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        registerListeners();
        registerCommands();

        //Spawn initialization
        if (Config.FIRSTCONFIGURATION) {

            Location world_dafault_spawn = Bukkit.getWorld("world").getSpawnLocation();

            Config.SPAWN_WORLD = world_dafault_spawn.getWorld().getName();
            Config.SPAWN_BLOCK_X = world_dafault_spawn.getX();
            Config.SPAWN_BLOCK_Z = world_dafault_spawn.getZ();
            Config.SPAWN_BLOCK_Y = world_dafault_spawn.getY();
            Config.SPAWN_YAW = world_dafault_spawn.getYaw();

            Config.FIRSTCONFIGURATION = false;
            Config.reloadConfig();
        }

        if (Config.COMBAT_ENABLED) {
            Bukkit.getScheduler().runTaskTimer(this, combatManager, 20, 20);
        }

        if (Config.STONEGENERATOR_ENABLE) {

            ItemStack item = new ItemStack(Material.END_STONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Config.STONEGENERATOR_GENERATORNAME);
            meta.setLore(Config.STONEGENERATOR_DESCRIPTION);
            item.setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "iocjow98345cj9673498yjw"), item)
                    .shape("RIR", "ISI", "RPR")
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('I', Material.IRON_INGOT)
                    .setIngredient('S', Material.STONE)
                    .setIngredient('P', Material.PISTON);
            plugin.getServer().addRecipe(recipe);

        }

        if (Config.RTP_ENABLE) rtpsStorage.loadButtons();

        PluginListeners.loadGroups();
        SethomeCMD.loadGroups();
    }

    @Override
    public void onDisable(){

        for(User u : usersStorage.getUsers().values()){

            if(!u.isUptodate()) {
                try {
                    usersStorage.saveUserData(u);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    public Chat getChat() {
        return chat;
    }

    public Permission getPermission() {
        return permission;
    }

    private void registerListeners(){
        consoleLog.info("Registering Listeners...");
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new CombatListeners(), this);
        pluginManager.registerEvents(new EasterEggListeners(), this);
        pluginManager.registerEvents(new PluginListeners(), this);
        pluginManager.registerEvents(new RandomTeleportListener(), this);
        pluginManager.registerEvents(new StoneGeneratorListeners(),this);
        pluginManager.registerEvents(new UserListeners(), this);
        pluginManager.registerEvents(new GUIListeners(), this);
    }

    private void registerCommands(){
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

        if(Config.RTP_ENABLE) CommandManager.registerCommand(new RandomCMD());

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

        if(Config.SKINS_ENABLE) CommandManager.registerCommand(new SkinCMD());

        CommandManager.registerCommand(new SpeedCMD());
        CommandManager.registerCommand(new WorkbenchCMD());
        CommandManager.registerCommand(new AnvilCMD());
        CommandManager.registerCommand(new TphereCMD());
        CommandManager.registerCommand(new TpallCMD());
        CommandManager.registerCommand(new TptoggleCMD());
        CommandManager.registerCommand(new EnchantCMD());
        CommandManager.registerCommand(new JumpCMD());
        CommandManager.registerCommand(new LightningCMD());
        CommandManager.registerCommand(new PingCMD());
        CommandManager.registerCommand(new WorldCMD());

        if(Config.WHITELIST_ENABLE) CommandManager.registerCommand(new WhitelistCMD());

        CommandManager.registerCommand(new GcCMD());

        CommandManager.registerCommand(new KitCMD());
    }

    public Connection getConnection() throws SQLException {
        return mysql.getConnection();
    }

    public CombatStorage getCombatStorage() {
        return combatStorage;
    }

    public Drops getDropsStorage() {
        return dropsStorage;
    }

    public Pms getPmsStorage() {
        return pmsStorage;
    }

    public Rtps getRtpsStorage() {
        return rtpsStorage;
    }

    public TpaStorage getTpaStorage() {
        return tpaStorage;
    }

    public Users getUsersStorage() {
        return usersStorage;
    }
}
