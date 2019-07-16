package com.paulek.core;

import com.paulek.core.basic.CombatManager;
import com.paulek.core.basic.User;
import com.paulek.core.basic.data.StorageManager;
import com.paulek.core.basic.data.databaseStorage.Skins;
import com.paulek.core.basic.data.databaseStorage.Timestamps;
import com.paulek.core.basic.data.databaseStorage.Users;
import com.paulek.core.basic.data.localStorage.CombatStorage;
import com.paulek.core.basic.data.localStorage.Pms;
import com.paulek.core.basic.data.localStorage.Rtps;
import com.paulek.core.basic.data.localStorage.TpaStorage;
import com.paulek.core.basic.database.Database;
import com.paulek.core.basic.database.MySQL;
import com.paulek.core.basic.database.SQLite;
import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.basic.listeners.*;
import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.common.ConsoleLog;
import com.paulek.core.common.Version;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Drops;
import com.paulek.core.common.io.Kits;
import com.paulek.core.common.io.Lang;
import com.sk89q.worldguard.WorldGuard;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Random;
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
    private WorldGuard worldGuard;
    private Database database;
    private StorageManager storageManager;
    private Skins skinsStorage;
    private String updateMethod;

    private boolean onlineMode;

    static {
        ConfigurationSerialization.registerClass(StoneDrop.class, "StoneDrop");
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        consoleLog = new ConsoleLog(this);

        Version version = new Version(this);
        version.chceckVersion();

        onlineMode = Bukkit.getOnlineMode();

//        //TODO FOR RESTS
//        drops.addDropMask(Material.STONE.name(), new BlockMask(this));
//        drops.getDrops().add(new StoneDrop("diamond", "$bMasz diaksa heheheheh", true, new ItemStack(Material.DIAMOND, 1), Arrays.asList(new ItemStack(Material.DIAMOND_PICKAXE, 1), new ItemStack(Material.IRON_PICKAXE, 1), new ItemStack(Material.GOLDEN_PICKAXE, 1)), 10, "drop.diamond", 7.91, true, "1-2", "<=30"));

        config = new Config(this);
        kits = new Kits(this);
        lang = new Lang(this);

        if (!Config.ENABLED || !plugin.isEnabled()) {
            consoleLog.log("Warning! Core disabled in config!", Level.WARNING);
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        //init database
        if(Config.STORAGETYPE.equalsIgnoreCase("MySQL")){
            MySQL mySQL = new MySQL(Config.MYSQL_HOST, Config.MYSQL_PORT, Config.MYSQL_DATABASE, Config.MYSQL_USER, Config.MYSQL_PASSWORD);
            mySQL.init();
            database = mySQL;

            try(Connection connection = database.getConnection()){

                PreparedStatement usersTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL , PRIMARY KEY (`id`))");

                PreparedStatement timestampsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `endTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `expired` TINYINT NOT NULL , PRIMARY KEY (`id`))");

                PreparedStatement skinsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`id`))");

                usersTabele.executeUpdate();
                usersTabele.close();

                timestampsTabele.executeUpdate();
                usersTabele.close();

                skinsTabele.executeUpdate();
                skinsTabele.close();
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

                PreparedStatement usersTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP NOT NULL , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL)");

                PreparedStatement timestampsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP NOT NULL , `endTime` TIMESTAMP NOT NULL , `expired` TINYINT NOT NULL)");

                PreparedStatement skinsTabele = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP NOT NULL)");

                usersTabele.executeUpdate();
                usersTabele.close();

                timestampsTabele.executeUpdate();
                usersTabele.close();

                skinsTabele.executeUpdate();
                skinsTabele.close();
                updateMethod = "ON CONFLICT(id) DO UPDATE SET";

            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }

        //init all storages
        combatStorage = new CombatStorage();
        //drops = new Drops(this);

        pmsStorage = new Pms();
        rtpsStorage = new Rtps();
        tpaStorage = new TpaStorage();
        usersStorage = new Users(this);
        usersStorage.init();
        timestamps = new Timestamps(this);
        timestamps.init();
        skinsStorage = new Skins(this);
        skinsStorage.init();

        combatManager = new CombatManager(this);

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
        if (this.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuard = WorldGuard.getInstance();
            consoleLog.info("WorldGuard detected!");
        } else {
            consoleLog.log("Warning! WorldGuard not detected! disabling plugin...", Level.WARNING);
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

            ShapedRecipe stoneGenerator = new ShapedRecipe(item)
                    .shape("RIR", "ISI", "RPR")
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('I', Material.IRON_INGOT)
                    .setIngredient('S', Material.STONE)
                    .setIngredient('P', Material.PISTON);

            plugin.getServer().addRecipe(stoneGenerator);


        }
    }

    @Override
    public void onDisable() {

        for (User u : usersStorage.getUsers().values()) {
            if (!u.isDirty()) {
                usersStorage.saveAllToDatabase(database);
            }
        }

        Bukkit.getScheduler().cancelTasks(plugin);


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
        commandManager.registerCommand(new TurboDropCMD(this));
    }

    public static String generateRandomString(int lenth){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(lenth);
        for (int i = 0; i < lenth; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return  buffer.toString().toUpperCase();
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

    public WorldGuard getWorldGuard() {
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
}
