package com.paulek.core.common.io;

import com.paulek.core.Core;
import com.paulek.core.common.ConsoleLog;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Config {

    private static final String prefix = "config.";
    public static boolean ENABLED = true;
    public static boolean FIRSTCONFIGURATION = true;
    public static boolean CENZOR_ENABLED = true;
    public static boolean SLOWDOWN_ENABLED = true;
    public static boolean CHAT_ENABLE_NOUPPERCASE = true;
    public static boolean CHAT_ENABLE = true;
    public static boolean SKINS_ENABLE = true;
    public static boolean STONEGENERATOR_ENABLE = true;
    public static boolean RTP_ENABLE = true;
    public static boolean WHITELIST_ENABLE = true;
    public static boolean COMBAT_ENABLED = true;
    public static boolean TP_SAFELOCATION = true;
    public static String STORAGETYPE = "json";
    public static String MYSQL_HOST = "localhost";
    public static String MYSQL_PORT = "3306";
    public static String MYSQL_DATABASE = "core";
    public static String MYSQL_USER = "root";
    public static String MYSQL_PASSWORD = "pass";
    public static int STONEGENERATOR_REGENERATETIME = 1;
    public static String STONEGENERATOR_GENERATORNAME = "§a§lGenerator";
    public static List<String> STONEGENERATOR_DESCRIPTION = Arrays.asList("§cPut a steward on the ground,", "§cPlace stone or obsidian on it", "§cFinished! Only the digger was left: D");
    public static int RTP_MAXVALUES_X = 5000;
    public static int RTP_MAXVALUES_Z = 5000;
    public static List<String> RTP_BUTTONLIST = Arrays.asList("");
    public static String WHITELIST_MOD = "$cYou are not in whitelist!";
    public static List<String> WHITELIST_ALLOWEDPLAYERS = Arrays.asList();
    public static boolean SKINS_HIDENONPREMIUM = true;
    public static List<String> SKINS_SKINSFORNONPREMIUM = Arrays.asList("Nick", "set");
    public static int TPA_DETLY = 10;
    public static int TPA_WAITINGTIME = 200;
    public static int BACK_DETLY = 20;
    public static int HOME_DETLY = 20;
    public static boolean EASTEREGGS = true;
    public static boolean ENCHANT_UNSAFE = true;
    public static boolean COMBAT_KILLONLOGOUT = true;
    public static boolean COMBAT_BRODCASTLOGOUT = true;
    public static boolean COMBAT_DISABLECHESTS = true;
    public static boolean COMBAT_DISABLEBREAKING = true;
    public static boolean COMBAT_DISABLEPLEACING = true;
    public static boolean COMBAT_DISABLECOMMAND = true;
    public static boolean COMBAT_CHATMESSAGE = true;
    public static boolean COMBAT_ONCREATIVE = true;
    public static boolean COMBAT_MOBDAMAGE = true;
    public static boolean COMBAT_DISCARD = true;
    public static String COMBAT_DISCARDBLOCK = "REDSTONE_BLOCK";
    public static int COMBAT_TIME = 20;
    public static List<String> COMBAT_IGNORED_COMMANDS = Arrays.asList("walka", "combat", "help", "clantilogout");
    public static List<String> COMBAT_IGNORED_BLOCKS = Arrays.asList("COBBLESTONE", "STONE");
    public static String DROP_GUINAME = "$cDropy";
    public static String DROP_ITEM_NAME = "$a{drop-name}";
    public static List<String> DROP_ITEM_LORE = Arrays.asList("Mozna wydropic: {can-drop}", "szansa: {chance}", "exp: {exp}", "dziala fortunka: {enabled-fortune}", "wypada na wysokosci: {height}", "Moze wypasc w ilosci {amount}", "mozna wydobyc dzieki: {tools}");
    public static boolean DROP_ONGAMEMODE = false;
    public static boolean DROP_VIP_ENABLE = true;
    public static double DROP_VIP_MULTIPLIER = 1.03;
    public static boolean DROP_HARDCORE = true;
    public static double DROP_FORTUNE_1_CHANCE = 20.91;
    public static String DROP_FORTUNE_1_AMOUNT = "1-2";
    public static double DROP_FORTUNE_2_CHANCE = 10.91;
    public static String DROP_FORTUNE_2_AMOUNT = "2-3";
    public static double DROP_FORTUNE_3_CHANCE = 2.91;
    public static String DROP_FORTUNE_3_AMOUNT = "3-4";
    public static int SPAWN_DETLY = 10;
    public static String SPAWN_WORLD = "world";
    public static double SPAWN_BLOCK_X = -1;
    public static double SPAWN_BLOCK_Z = 10;
    public static double SPAWN_BLOCK_Y = 89177777777234789.0;
    public static double SPAWN_YAW = 0;
    public static boolean VANISH_SILIENTJOIN = true;
    public static boolean VANISH_DISALLOWINTERACT = true;
    public static boolean VANISH_DISALLOWPICKUP = true;
    public static boolean VANISH_DISALLOWFIGHT = true;
    public static List<String> HOME_AMOUNT = Arrays.asList("root 10", "default 1");
    public static Integer CHAT_SLOWDOWN = 5;
    public static List<String> CHAT_FORMATING = Arrays.asList("root $c{displayname} $6» $c{message}", "default $a{displayname} $6» $f{message}");
    public static String CHAT_CENZOREDREPLACE = "xxx";
    public static List<String> CHAT_BLACKLISTEDWORDS = Arrays.asList("idiota", "debil", "huj", "stara", "cycki"
            , "idiota", "sperma", "kurwa", "kórwa", "zajebiscie", "cipa", "fuck", "wypierdlalaj", "cwel", "dupcyc");
    private Core core;

    public Config(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        reloadConfig();
    }

    public void loadConfig() {
        try {
            FileConfiguration c = core.getPlugin().getConfig();

            for (Field f : Config.class.getFields()) {

                if (c.isSet(prefix + f.getName().toLowerCase().replace("_", ".")))
                    f.set(null, c.get(prefix + f.getName().toLowerCase().replace("_", ".")));

            }

        } catch (Exception e) {
            core.getConsoleLog().warning(e.getMessage());
        }
    }

    public void saveConfig() {
        try {
            FileConfiguration c = core.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                c.set(prefix + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            core.getPlugin().saveConfig();
        } catch (Exception e) {
            core.getConsoleLog().warning(e.getMessage());
        }
    }

    public void reloadConfig() {
        core.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }

}
