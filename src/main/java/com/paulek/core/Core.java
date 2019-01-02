package com.paulek.core;

import com.paulek.core.commands.CommandManager;
import com.paulek.core.commands.cmds.admin.*;
import com.paulek.core.commands.cmds.user.*;
import com.paulek.core.data.RandomtpStorage;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.User;
import com.paulek.core.listeners.block.BlockBreakEvent;
import com.paulek.core.listeners.block.BlockPlaceEvent;
import com.paulek.core.listeners.entity.EntityDamageByEntityEvent;
import com.paulek.core.listeners.player.PlayerShearEntityEvent;
import com.paulek.core.listeners.player.*;
import com.paulek.core.managers.CombatManager;
import com.paulek.core.managers.MySQL;
import com.paulek.core.utils.consoleLog;
import com.paulek.core.utils.version;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class Core extends JavaPlugin{

    private static Core plugin;
    private static Permission permission = null;
    private static Chat chat = null;
    private static MySQL mysql;

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
        if (Config.SETTINGS_SPAWN_BLOCKY == 89177777777234789.0 && Config.SETTINGS_SPAWN_BLOCKX == -1 && Config.SETTINGS_SPAWN_BLOCKZ == 10) {

            Location world_dafault_spawn = Bukkit.getWorld("world").getSpawnLocation();

            Config.SETTINGS_SPAWN_WORLD = world_dafault_spawn.getWorld().getName();
            Config.SETTINGS_SPAWN_BLOCKX = world_dafault_spawn.getX();
            Config.SETTINGS_SPAWN_BLOCKZ = world_dafault_spawn.getZ();
            Config.SETTINGS_SPAWN_BLOCKY = world_dafault_spawn.getY();
            Config.SETTINGS_SPAWN_YAW = world_dafault_spawn.getYaw();

        }

        if (Config.SETTINGS_COMBAT_ENABLED) {
            Bukkit.getScheduler().runTaskTimer(this, new CombatManager(), 20, 20);
        }

        if (Config.ENABLE_STONEGENERATOR) {

            ItemStack item = new ItemStack(Material.END_STONE);
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
                    .setIngredient('P', Material.PISTON);
            plugin.getServer().addRecipe(recipe);

        }

        if (Config.ENABLE_RANDOMTELEPORT) RandomtpStorage.loadButtons();

        AsyncPlayerChatEvent.loadGroups();

        File folder = new File("./plugins/clCore/users/");

        for(File f : folder.listFiles()){

            String uuid = f.getName().split(".")[0];

            User user = new User(uuid);

            UserStorage.getUsers().put(UUID.fromString(uuid), user);

        }

        //TODO
        //Ładowanie userów z bazy wszystkich
//        if(mysql != null){
//
//            try{
//
//                Connection connection = mysql.getConnection();
//
//                try{
//
//                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
//
//                    ResultSet users = statement.executeQuery();
//
//                    while(users.next()){
//
//
//                        String uuid = users.getString("uuid");
//
//                        //User user = new User(UUID.fromString(uuid));
//
//                        //UserStorage.getUsers().put(UUID.fromString(uuid), user);
//
//
//                    }
//
//                    statement.close();
//                } finally {
//                    connection.close();
//                }
//
//                if(!connection.isClosed()) connection.close();
//            } catch (SQLException e){
//
//                e.printStackTrace();
//
//            }
//      }
        
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

    private void registerListeners(){
        consoleLog.info("Registering Listeners...");
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), plugin);
        pluginManager.registerEvents(new PlayerMoveEvent(), plugin);
        pluginManager.registerEvents(new PlayerTeleportEvent(), plugin);
        pluginManager.registerEvents(new PlayerRespawnEvent(), plugin);
        pluginManager.registerEvents(new PlayerJoinEvent(), plugin);
        pluginManager.registerEvents(new PlayerQuitEvent(), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityEvent(), plugin);
        pluginManager.registerEvents(new PlayerCommandPreprocessEvent(), plugin);
        pluginManager.registerEvents(new PlayerInteractEvent(), plugin);
        pluginManager.registerEvents(new BlockBreakEvent(), plugin);
        pluginManager.registerEvents(new BlockPlaceEvent(), plugin);
        pluginManager.registerEvents(new PlayerShearEntityEvent(), plugin);
        pluginManager.registerEvents(new PlayerDeathEvent(), plugin);
        pluginManager.registerEvents(new AsyncPlayerPreLoginEvent(), plugin);
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
        CommandManager.registerCommand(new EnchantCMD());
        CommandManager.registerCommand(new JumpCMD());
        CommandManager.registerCommand(new LightningCMD());
        CommandManager.registerCommand(new PingCMD());
        CommandManager.registerCommand(new WorldCMD());

        if(Config.ENABLE_WHITELIST) CommandManager.registerCommand(new WhitelistCMD());
    }

    public static Connection getConnection() throws SQLException {
        return mysql.getConnection();
    }
}
