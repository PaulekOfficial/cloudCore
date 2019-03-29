package com.paulek.core;

import com.paulek.core.basic.CombatManager;
import com.paulek.core.basic.MySQL;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.*;
import com.paulek.core.basic.listeners.*;
import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.common.ConsoleLog;
import com.paulek.core.common.Version;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Kits;
import com.paulek.core.common.io.Lang;
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

public class Core extends JavaPlugin {

    public boolean chatEnabled = true;
    private Plugin plugin;
    private Permission permission = null;
    private ConsoleLog consoleLog;
    private Chat chat = null;
    private MySQL mysql;
    private Config config;
    private Kits kits;
    private Lang lang;
    private CombatManager combatManager;
    private CommandManager commandManager;
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

        Version version = new Version(this);
        version.chceckVersion();

        consoleLog = new ConsoleLog(this);

        config = new Config(this);
        kits = new Kits(this);
        lang = new Lang(this);

        if (!Config.ENABLED) {
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        combatManager = new CombatManager(this);

        //init all storages
        combatStorage = new CombatStorage();
        dropsStorage = new Drops();
        pmsStorage = new Pms();
        rtpsStorage = new Rtps();
        tpaStorage = new TpaStorage();
        usersStorage = new Users(this);

        commandManager = new CommandManager(this);


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
    }

    @Override
    public void onDisable() {

        for (User u : usersStorage.getUsers().values()) {

            if (!u.isUptodate()) {
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

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public Chat getChat() {
        return chat;
    }

    public Permission getPermission() {
        return permission;
    }

    private void registerListeners() {
        consoleLog.info("Registering Listeners...");
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new CombatListeners(this), this);
        pluginManager.registerEvents(new EasterEggListeners(), this);
        pluginManager.registerEvents(new PluginListeners(this), this);
        pluginManager.registerEvents(new RandomTeleportListener(this), this);
        pluginManager.registerEvents(new StoneGeneratorListeners(this), this);
        pluginManager.registerEvents(new UserListeners(this), this);
        pluginManager.registerEvents(new GUIListeners(), this);
    }

    private void registerCommands() {
        consoleLog.info("Registering Commands...");
        commandManager.registerCommand(new CoreCMD(this));
        commandManager.registerCommand(new ChatCMD(this));
        commandManager.registerCommand(new GmCMD(this));
        commandManager.registerCommand(new TpCMD(this));
        commandManager.registerCommand(new FlyCMD(this));
        commandManager.registerCommand(new HelpopCMD(this));
        commandManager.registerCommand(new WeatherCMD(this));
        commandManager.registerCommand(new TimeCMD(this));
        commandManager.registerCommand(new SpawnCMD(this));
        commandManager.registerCommand(new BackCMD(this));
        commandManager.registerCommand(new SetSpawnCMD(this));
        commandManager.registerCommand(new TpacceptCMD(this));
        commandManager.registerCommand(new TpaCMD(this));
        commandManager.registerCommand(new CombatCMD(this));
        commandManager.registerCommand(new DayCMD(this));
        commandManager.registerCommand(new NightCMD(this));

        if (Config.RTP_ENABLE) commandManager.registerCommand(new RandomCMD(this));

        commandManager.registerCommand(new VanishCMD(this));
        commandManager.registerCommand(new MsgCMD(this));
        commandManager.registerCommand(new MsgspyCMD(this));
        commandManager.registerCommand(new RCMD(this));
        commandManager.registerCommand(new BrodcastCMD(this));
        commandManager.registerCommand(new InvseeCMD(this));
        commandManager.registerCommand(new EnderchestCMD(this));
        commandManager.registerCommand(new SethomeCMD(this));
        commandManager.registerCommand(new HomeCMD(this));
        commandManager.registerCommand(new DragondeathCMD(this));
        commandManager.registerCommand(new SunCMD(this));
        commandManager.registerCommand(new DelhomeCMD(this));
        commandManager.registerCommand(new TpahereCMD(this));
        commandManager.registerCommand(new TpadenyCMD(this));

        if (Config.SKINS_ENABLE) commandManager.registerCommand(new SkinCMD(this));

        commandManager.registerCommand(new SpeedCMD(this));
        commandManager.registerCommand(new WorkbenchCMD(this));
        commandManager.registerCommand(new AnvilCMD(this));
        commandManager.registerCommand(new TphereCMD(this));
        commandManager.registerCommand(new TpallCMD(this));
        commandManager.registerCommand(new TptoggleCMD(this));
        commandManager.registerCommand(new EnchantCMD(this));
        commandManager.registerCommand(new JumpCMD(this));
        commandManager.registerCommand(new LightningCMD(this));
        commandManager.registerCommand(new PingCMD(this));
        commandManager.registerCommand(new WorldCMD(this));

        if (Config.WHITELIST_ENABLE) commandManager.registerCommand(new WhitelistCMD(this));

        commandManager.registerCommand(new GcCMD(this));

        commandManager.registerCommand(new KitCMD(this));
    }

    public Connection getConnection() throws SQLException {
        return mysql.getConnection();
    }

    public Config getConfiguration() {
        return config;
    }

    public Lang getLang() {
        return lang;
    }

    public Kits getKits() {
        return kits;
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

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    public ConsoleLog getConsoleLog() {
        return consoleLog;
    }
}
