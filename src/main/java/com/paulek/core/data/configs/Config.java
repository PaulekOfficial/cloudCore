package com.paulek.core.data.configs;

import com.paulek.core.Core;
import com.paulek.core.utils.consoleLog;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Config {

    private static final String prefix = "config.";

    public static boolean ENABLED = false;
    public static boolean ENABLE_CENZOR = true;
    public static boolean ENABLE_SLOWDOWN = true;
    public static boolean ENABLE_NOUPPERCASE = true;
    public static boolean ENABLE_CHAT = true;
    public static boolean ENABLE_SKINS = true;
    public static boolean ENABLE_STONEGENERATOR = true;
    public static boolean ENABLE_RANDOMTELEPORT = true;

    public static boolean SETTINGS_SAFELOCATION = true;

    public static int SETTINGS_STONEGENERATOR_REGENERATE = 1;
    public static String SETTINGS_STONEGENERATOR_NAME = "§a§lGenerator";
    public static List<String> SETTINGS_STONEGENERATOR_LORE = Arrays.asList("§cPut a steward on the ground,", "§cPlace stone or obsidian on it", "§cFinished! Only the digger was left: D");

    public static int SETTINGS_RANDOMTELEPORT_MAX_X = 5000;
    public static int SETTINGS_RANDOMTELEPORT_MAX_Z = 5000;
    public static List<String> SETTINGS_RANDOMTELEPORT_BUTTONS = Arrays.asList("");

    public static boolean SETTINGS_SKINS_ENABLENOPREMIUMRANDOMSKIN = true;

    public static List<String> SETTINGS_NONPREMIUMSKINS = Arrays.asList("Nick", "set");

    public static int SETTINGS_TPA_DETLY = 10;
    public static int SETTINGS_TPA_WAITINGTOACCEPT = 200;

    public static int SETTINGS_BACK_DETLY = 20;

    public static int SETTINGS_HOME_DETLY = 20;

    public static boolean SETTINGS_EASTEREGG_EXPLODESHEEP = true;

    public static boolean SETTINGS_COMBAT_ENABLED = true;
    public static boolean SETTINGS_COMBAT_KILLONLOGOUT = true;
    public static boolean SETTINGS_COMBAT_BRODCASTLOGOUT = true;
    public static boolean SETTINGS_COMBAT_DISABLECHESTS = true;
    public static boolean SETTINGS_COMBAT_DISABLEBREAKING = true;
    public static boolean SETTINGS_COMBAT_DISABLEPLEACING = true;
    public static boolean SETTINGS_COMBAT_DISABLECOMMAND = true;
    public static boolean SETTINGS_COMBAT_CHATMESSAGE = true;
    public static boolean SETTINGS_COMBAT_CREATIVE = true;
    public static boolean SETTINGS_COMBAT_MOBDAMAGE = true;
    public static boolean SETTINGS_COMBAT_DISCARD = true;
    public static String SETTINGS_COMBAT_DISCARDBLOCK = "REDSTONE_BLOCK";
    public static int SETTINGS_COMBAT_TIME = 20;
    public static List<String> SETTINGS_COMBAT_IGNORED_COMMANDS = Arrays.asList("walka", "combat", "help", "clantilogout");
    public static List<String> SETTINGS_COMBAT_IGNORED_PLACE = Arrays.asList("COBBLESTONE", "STONE");

    public static int SETTINGS_SPAWN_DETLY = 10;
    public static String SETTINGS_SPAWN_WORLD = "world";
    public static double SETTINGS_SPAWN_BLOCKX = 0;
    public static double SETTINGS_SPAWN_BLOCKZ = 0;
    public static double SETTINGS_SPAWN_BLOCKY = 100;
    public static double SETTINGS_SPAWN_YAW = 0;

    public static boolean SETTINGS_SAVE_GAMEMODE = true;
    public static boolean SETTINGS_SAVE_FLY = true;
    public static boolean SETTINGS_SAVE_GOD = true;
    public static boolean SETTINGS_SAVE_SOCIALSPY = true;
    public static boolean SETTINGS_SAVE_VANISH = true;

    public static boolean SETTINGS_VANISH_SILIENTJOIN = true;
    public static boolean SETTINGS_VANISH_DISALLOWINTERACT = true;
    public static boolean SETTINGS_VANISH_DISALLOWPICKUP = true;
    public static boolean SETTINGS_VANISH_DISALLOWFIGHT = true;

    public static List<String> HOME_AMOUNT = Arrays.asList("root 10", "default 1");

    public static Integer CHAT_SLOWDOWN = 5;
    public static List<String> CHAT_FORMATING = Arrays.asList("root $c{displayname} $6» $c{message}", "default $a{displayname} $6» $f{message}");
    public static String CHAT_CENZOREDREPLACE = "xxx";
    public static List<String> BLACKLISTED_WORDS = Arrays.asList("idiota", "debil", "huj", "stara", "cycki"
    , "idiota", "sperma", "kurwa", "kórwa", "zajebiscie", "cipa", "fuck", "wypierdlalaj", "cwel", "dupcyc");


    public static void loadConfig(){
        try{
            FileConfiguration c = Core.getPlugin().getConfig();

            for(Field f : Config.class.getFields()){

                if (c.isSet(prefix + f.getName().toLowerCase().replace("_", ".")))
                    f.set(null, c.get(prefix + f.getName().toLowerCase().replace("_", ".")));

            }

        } catch (Exception e){
            consoleLog.warning(e.getMessage());
        }
    }

    public static void saveConfig() {
        try {
            FileConfiguration c = Core.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                c.set(prefix + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            Core.getPlugin().saveConfig();
        } catch (Exception e) {
            consoleLog.warning(e.getMessage());
        }
    }

    public static void reloadConfig(){
        Core.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }

}
