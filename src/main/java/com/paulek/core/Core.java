package com.paulek.core;

import com.paulek.core.basic.Kit;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.*;
import com.paulek.core.basic.database.Database;
import com.paulek.core.basic.database.MySQL;
import com.paulek.core.basic.database.SQLite;
import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.basic.listeners.*;
import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.common.*;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Drops;
import com.paulek.core.common.io.Kits;
import com.paulek.core.common.io.Lang;
import com.sk89q.worldguard.WorldGuard;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class Core extends JavaPlugin {

    public boolean chatEnabled = true;
    private Plugin plugin;
    private Permission permission = null;
    private ConsoleLog consoleLog;
    private Chat chat = null;
    private Config config;
    private Kits kits;
    private Lang lang;
    private CommandManager commandManager;
    private Drops drops;
    private Users usersStorage;
    private Skins skinsStorage;
    private PrivateMessages privateMessagesStorage;
    private TeleportRequests teleportRequestsStorage;
    private Object worldGuard;
    private Combats combatsStorage;
    private Database database;
    private Version version;
    private DataModel dataModel;

    private boolean onlineMode;

    //TODO remove this
    static {
        ConfigurationSerialization.registerClass(StoneDrop.class, "StoneDrop");
        ConfigurationSerialization.registerClass(Kit.class,  "Kit");
    }

    @SuppressWarnings("Deprecated")
    @Override
    public void onEnable() {
        plugin = this;

        //init console
        consoleLog = new ConsoleLog(this);

        //version check
        version = new Version(this);
        if(!version.isVersionOk()){
            return;
        }

        //set online mode statis
        onlineMode = Bukkit.getOnlineMode();

        //init configuration files
        initConfigs();

        if (!config.enabled || !plugin.isEnabled()) {
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        //init database
        initDatabase();

        //init all storages
        initStorages();

        //Valut initialization
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

        //WorldGuard initialization
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuard = WorldGuard.getInstance();
            consoleLog.info("WorldGuard detected!");
        } else {
            //TODO Should disable modules, that use worldguard, not disable this plugin
            consoleLog.log("Warning! WorldGuard not detected! disabling plugin...", Level.WARNING);
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }

        //init listeners
        registerListeners();

        //init commands
        //TODO Rewrite all commands, (bad designed)
        registerCommands();

        //init combatlog
        //TODO Fix combat issues on flag no pvp
        if (config.combatlogEnabled) {
            Bukkit.getScheduler().runTaskTimer(this, combatManager, 20, 20);
        }

        //TODO Rewrite it with full customization
        registerStoneGenerator();

        if(!config.teleportSafetyWater){
            LocationUtil.disallowLiquids();
        }

    }

    @Override
    public void onDisable() {

        Bukkit.getScheduler().cancelTasks(plugin);
        usersStorage.saveDataBeforeShutdown();
        skinsStorage.saveDataBeforeShutdown();

    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getVersion() {
        return version.getVersion();
    }

    public Chat getChat() {
        return chat;
    }

    public Permission getPermission() {
        return permission;
    }

    private void registerStoneGenerator(){
        if (config.generatorEnabled) {

            ItemStack item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(config.generatorBlock)));
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ColorUtil.fixColor(config.genertorName));
            meta.setLore(ColorUtil.fixColors(config.generatorLore.toArray(new String[0])));
            item.setItemMeta(meta);

            ShapedRecipe stoneGenerator = new ShapedRecipe(new NamespacedKey(plugin, "stone_generator"), item)
                    .shape("ABC", "DEF", "GHI")
                    .setIngredient('A', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(1))))
                    .setIngredient('B', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(2))))
                    .setIngredient('C', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(3))))
                    .setIngredient('D', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(4))))
                    .setIngredient('E', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(5))))
                    .setIngredient('F', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(6))))
                    .setIngredient('G', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(7))))
                    .setIngredient('H', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(8))))
                    .setIngredient('I', Objects.requireNonNull(Material.matchMaterial(config.generatorCrafting.get(9))));

            plugin.getServer().addRecipe(stoneGenerator);


        }
    }

    private void initConfigs(){
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }

        File configFile = new File(this.getDataFolder(), "config.yml");

        config = ConfigUtil.loadConfig(configFile, Config.class);
        Config.SKIN_API = config.skinsApi;
        Config.SKIN_SIGNATURE = config.skinsSignature;
        Config.PROFILE_API = config.profileAPI;
        Config.COLOR_MARK = config.colorMark;
        kits = new Kits(this);
        kits.init();
        lang = new Lang(this);
    }

    private void initDatabase(){
        //Logger.getLogger("com.zaxxer.hikari").isLoggable(Level.FINEST);
        dataModel = DataModel.getModelByName(config.storageType);
        if(dataModel.equals(DataModel.MYSQL)) {

            String host = config.mysql.get("host");
            String port = config.mysql.get("port");
            String databaseName = config.mysql.get("database-name");
            String user = config.mysql.get("user");
            String password = config.mysql.get("password");

            MySQL mySQL = new MySQL(host, port, databaseName, user, password);
            mySQL.init();
            database = mySQL;

        } else if(dataModel.equals(DataModel.SQLITE)) {
            File databaseFile = new File(plugin.getDataFolder(), "database.db");

            if(!databaseFile.exists()){
                try {
                    databaseFile.createNewFile();
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
            SQLite sqLite = new SQLite(databaseFile);
            sqLite.init();
            database = sqLite;
        }

    }

    private void initStorages(){
        //TODO Better storage classes design
        //drops = new Drops(this);

        usersStorage = new Users(this, dataModel);
        usersStorage.init();
        if(config.combatlogEnabled) {
            combatsStorage = new Combats(this);
            combatsStorage.init();
        }
        commandManager = new CommandManager();
        privateMessagesStorage = new PrivateMessages();
        skinsStorage = new Skins(this);
        skinsStorage.init();
    }

    private void registerListeners() {
        consoleLog.info("Registering Listeners...");
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        if(config.chatModuleEnabled){
            ChatListeners chatListeners = new ChatListeners(this);
            chatListeners.loadGroups();
            pluginManager.registerEvents(chatListeners, this);
        }
        if(config.skinsEnabled){
            pluginManager.registerEvents(new SkinListeners(this), this);
        }
        if(config.combatlogEnabled){
            pluginManager.registerEvents(new CombatListeners(this), this);
        }
        if(config.easteregg){
            pluginManager.registerEvents(new EasterEggListeners(this), this);
        }
        pluginManager.registerEvents(new CacheListeners(this), this);
        if(config.rtpEnabled){
            pluginManager.registerEvents(new RandomTeleportListener(this), this);
        }
        if (config.generatorEnabled) {
            pluginManager.registerEvents(new StoneGeneratorListeners(this), this);
        }
        if(config.godmode) {
            pluginManager.registerEvents(new GodModeListener(this), this);
        }
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
        commandManager.registerCommand(new CombatCMD(this));
        commandManager.registerCommand(new DayCMD(this));
        commandManager.registerCommand(new NightCMD(this));

        if (config.rtpEnabled) commandManager.registerCommand(new RandomCMD(this));

        commandManager.registerCommand(new VanishCMD(this));
        commandManager.registerCommand(new MsgCMD(this));
        commandManager.registerCommand(new MsgspyCMD(this));
        commandManager.registerCommand(new RCMD(this));
        commandManager.registerCommand(new BrodcastCMD(this));
        commandManager.registerCommand(new InvseeCMD(this));
        commandManager.registerCommand(new EnderchestCMD(this));
        SethomeCMD sethomeCMD = new SethomeCMD(this);
        sethomeCMD.loadGroups();
        commandManager.registerCommand(sethomeCMD);
        commandManager.registerCommand(new HomeCMD(this));
        commandManager.registerCommand(new SunCMD(this));
        commandManager.registerCommand(new DelhomeCMD(this));

        if(config.tpaEnabled){
            commandManager.registerCommand(new TpacceptCMD(this));
            commandManager.registerCommand(new TpaCMD(this));
            commandManager.registerCommand(new TpahereCMD(this));
            commandManager.registerCommand(new TpadenyCMD(this));
        }

        if (config.skinsEnabled && config.skinsCommand) commandManager.registerCommand(new SkinCMD(this));

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

        //if (Config.WHITELIST_ENABLE) commandManager.registerCommand(new WhitelistCMD(this));

        commandManager.registerCommand(new GcCMD(this));
        commandManager.registerCommand(new KitCMD(this));
        commandManager.registerCommand(new TurboDropCMD(this));

        if(config.godmode) commandManager.registerCommand(new GodCMD(this));
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

    public Combats getCombatsStorage() {
        return combatsStorage;
    }

    public Drops getDrops() {
        return drops;
    }

    public Rtps getRtpsStorage() {
        return rtpsStorage;
    }

    public TeleportRequests getTeleportRequestsStorage() {
        return teleportRequestsStorage;
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

    public Object getWorldGuard() {
        return worldGuard;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public Database getDatabase() {
        return database;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public PrivateMessages getPrivateMessagesStorage() {
        return privateMessagesStorage;
    }

    public Skins getSkinsStorage() {
        return skinsStorage;
    }

    public Spawns getSpawnsStorage() {
        return spawnsStorage;
    }
}
