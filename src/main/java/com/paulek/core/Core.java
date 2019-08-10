package com.paulek.core;

import com.paulek.core.basic.CombatManager;
import com.paulek.core.basic.Kit;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.StorageManager;
import com.paulek.core.basic.data.databaseStorage.*;
import com.paulek.core.basic.data.localStorage.CombatStorage;
import com.paulek.core.basic.data.localStorage.Pms;
import com.paulek.core.basic.data.localStorage.TpaStorage;
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
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private CombatManager combatManager;
    private CommandManager commandManager;
    private CombatStorage combatStorage;
    private Drops drops;
    private Pms pmsStorage;
    private Rtps rtpsStorage;
    private TpaStorage tpaStorage;
    private Timestamps timestamps;
    private Users usersStorage;
    private Object worldGuard;
    private Database database;
    private StorageManager storageManager;
    private Skins skinsStorage;
    private Spawns spawnsStorage;
    private String updateMethod;
    private Version version;

    private boolean onlineMode;

    static {
        ConfigurationSerialization.registerClass(StoneDrop.class, "StoneDrop");
        ConfigurationSerialization.registerClass(Kit.class,  "Kit");
    }

    @SuppressWarnings("Deprecated")
    @Override
    public void onEnable() {
        plugin = this;

        consoleLog = new ConsoleLog(this);

        version = new Version(this);
        if(!version.isVersionOk()){
            return;
        }

        onlineMode = Bukkit.getOnlineMode();

//        //TODO FOR RESTS
//        drops.addDropMask(Material.STONE.name(), new BlockMask(this));
//        drops.getDrops().add(new StoneDrop("diamond", "$bMasz diaksa heheheheh", true, new ItemStack(Material.DIAMOND, 1), Arrays.asList(new ItemStack(Material.DIAMOND_PICKAXE, 1), new ItemStack(Material.IRON_PICKAXE, 1), new ItemStack(Material.GOLDEN_PICKAXE, 1)), 10, "drop.diamond", 7.91, true, "1-2", "<=30"));

        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }

        File configFile = new File(this.getDataFolder(), "config.yml");

        config = ConfigUtil.loadConfig(configFile, Config.class);
        kits = new Kits(this);
        kits.init();
        lang = new Lang(this);

        if (!config.enabled || !plugin.isEnabled()) {
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        //init database
        if(config.storageType.toLowerCase().equalsIgnoreCase("mysql")){

            String host = config.mysql.get("host");
            String port = config.mysql.get("port");
            String databaseName = config.mysql.get("database-name");
            String user = config.mysql.get("user");
            String password = config.mysql.get("password");

            MySQL mySQL = new MySQL(host, port, databaseName, user, password);
            mySQL.init();
            database = mySQL;

            try(Connection connection = database.getConnection()){

                PreparedStatement usersTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL , `godMode` TINYINT NOT NULL , PRIMARY KEY (`id`))");

                PreparedStatement timestampsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `endTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `expired` TINYINT NOT NULL , PRIMARY KEY (`id`))");

                PreparedStatement skinsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`id`))");

                PreparedStatement spawnsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_spawns` ( `id` INT NOT NULL AUTO_INCREMENT , `name` INT NOT NULL , `world` TEXT NOT NULL ,`x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , `pitch` FLOAT NOT NULL , `yaw` FLOAT NOT NULL , PRIMARY KEY (`id`))");

                PreparedStatement buttonsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_buttons` ( `id` INT NOT NULL AUTO_INCREMENT , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , PRIMARY KEY (`id`))");

                usersTabele.executeUpdate();
                usersTabele.close();

                timestampsTabele.executeUpdate();
                usersTabele.close();

                skinsTabele.executeUpdate();
                skinsTabele.close();

                spawnsTabele.executeUpdate();
                spawnsTabele.close();

                buttonsTabele.executeUpdate();
                buttonsTabele.close();
                updateMethod = "ON DUPLICATE KEY UPDATE";

            } catch (SQLException exception){
                exception.printStackTrace();
            }
        } else {
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

            try(Connection connection = database.getConnection()){

                PreparedStatement usersTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP NOT NULL , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL, `godMode` TINYINT NOT NULL)");

                PreparedStatement timestampsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP NOT NULL , `endTime` TIMESTAMP NOT NULL , `expired` TINYINT NOT NULL)");

                PreparedStatement skinsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP NOT NULL)");

                PreparedStatement spawnsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_spawns` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` INT NOT NULL , `world` TEXT NOT NULL ,`x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , `pitch` FLOAT NOT NULL , `yaw` FLOAT NOT NULL)");

                PreparedStatement buttonsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_buttons` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL)");

                usersTabele.executeUpdate();
                usersTabele.close();

                timestampsTabele.executeUpdate();
                usersTabele.close();

                skinsTabele.executeUpdate();
                skinsTabele.close();

                spawnsTabele.executeUpdate();
                spawnsTabele.close();

                buttonsTabele.executeUpdate();
                buttonsTabele.close();
                updateMethod = "ON CONFLICT(id) DO UPDATE SET";

            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }

        //init all storages
        combatStorage = new CombatStorage();
        //drops = new Drops(this);

        pmsStorage = new Pms();
        if(config.rtpEnabled) rtpsStorage = new Rtps(this);
        if(config.tpaEnabled) tpaStorage = new TpaStorage();
        usersStorage = new Users(this);
        usersStorage.init();
        timestamps = new Timestamps(this);
        timestamps.init();
        if(config.skinsEnabled) {
            skinsStorage = new Skins(this);
            skinsStorage.init();
        }
        spawnsStorage = new Spawns(this);
        spawnsStorage.init();

        if(config.combatlogEnabled) combatManager = new CombatManager(this);

        commandManager = new CommandManager(this);

        storageManager = new StorageManager(this, database);
        storageManager.addManagedStorage(timestamps);
        storageManager.addManagedStorage(usersStorage);
        storageManager.addManagedStorage(skinsStorage);
        storageManager.init();


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
            try{
                Class worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
                Method getInstance = ReflectionUtils.getMethod(worldGuardClass, "getInstance");
                worldGuard = getInstance.invoke(null, null);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                try {
                    Class worldGuardClass = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin");
                    Method getInstance = ReflectionUtils.getMethod(worldGuardClass, "inst");
                    worldGuard = getInstance.invoke(null, null);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            consoleLog.info("WorldGuard detected!");
        } else {
            consoleLog.log("Warning! WorldGuard not detected! disabling plugin...", Level.WARNING);
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }

        registerListeners();
        registerCommands();

        if (config.combatlogEnabled) {
            Bukkit.getScheduler().runTaskTimer(this, combatManager, 20, 20);
        }

        if (config.generatorEnabled) {

            ItemStack item = new ItemStack(Material.matchMaterial(config.generatorBlock));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ColorUtil.fixColor(config.genertorName));
            meta.setLore(ColorUtil.fixColors(config.generatorLore.toArray(new String[0])));
            item.setItemMeta(meta);

            ShapedRecipe stoneGenerator = new ShapedRecipe(item)
                    .shape("ABC", "DEF", "GHI")
                    .setIngredient('A', Material.matchMaterial(config.generatorCrafting.get(1)))
                    .setIngredient('B', Material.matchMaterial(config.generatorCrafting.get(2)))
                    .setIngredient('C', Material.matchMaterial(config.generatorCrafting.get(3)))
                    .setIngredient('D', Material.matchMaterial(config.generatorCrafting.get(4)))
                    .setIngredient('E', Material.matchMaterial(config.generatorCrafting.get(5)))
                    .setIngredient('F', Material.matchMaterial(config.generatorCrafting.get(6)))
                    .setIngredient('G', Material.matchMaterial(config.generatorCrafting.get(7)))
                    .setIngredient('H', Material.matchMaterial(config.generatorCrafting.get(8)))
                    .setIngredient('I', Material.matchMaterial(config.generatorCrafting.get(9)));

            plugin.getServer().addRecipe(stoneGenerator);


        }

        if(!config.teleportSafetyWater){
            LocationUtil.disallowLiquids();
        }

    }

    @Override
    public void onDisable() {

        if(usersStorage != null) {
            if(usersStorage.getUsers() != null) {
                for (User u : usersStorage.getUsers().values()) {
                    if (!u.isDirty()) {
                        usersStorage.saveAllToDatabase(database);
                    }
                }
            }
        }

        Bukkit.getScheduler().cancelTasks(plugin);


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
        pluginManager.registerEvents(new StorageListeners(this), this);
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

    public CombatStorage getCombatStorage() {
        return combatStorage;
    }

    public Drops getDrops() {
        return drops;
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

    public Skins getSkinsStorage() {
        return skinsStorage;
    }

    public String getUpdateMethod() {
        return updateMethod;
    }

    public Spawns getSpawnsStorage() {
        return spawnsStorage;
    }
}
