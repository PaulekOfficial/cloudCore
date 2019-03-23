package com.paulek.core.common.io;

import com.paulek.core.Core;
import com.paulek.core.common.Util;
import com.paulek.core.common.consoleLog;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Lang {

    private static final String prefix = "lang.";

    public static String ERROR_TP_NOTCORDINATES = "$eThats are not cordinates!";
    public static String ERROR_MUSTBEPLAYER = "Error! You must be a player to run this!";
    public static String ERROR_TP_NOTONLINE = "$cError! Specificed player must be online!";
    public static String ERROR_CHAT_DISABLEDTYPE = "$eError! Chat is disabled!";
    public static String ERROR_CHAT_ENABLED = "$cChat is arledy on";
    public static String ERROR_CHAT_DISABLED = "$cChat is arledy off";
    public static String ERROR_CHAT_SLOWDOWN = "$eError! You must wait 5 secounds before typing!";
    public static String ERROR_THATSNOTANUMBER = "$eError! That is not a number!";
    public static String INFO_CORE_RELOAD = "$cCore reloaded!";
    public static String INFO_CORE_DISABLEING = "$cWarning serwer is shutingdown!";
    public static String USAGE = "$cUsage: $e{usage}";
    public static String INFO_TIME_SET = "$aSet time to {time} in {world}";
    public static String ERROR_TIME_NOWORLD = "$eSpecific world dosn't exist";
    public static String INFO_TP_INFO = "$eTeleported to {player}";
    public static String INFO_HELPOP_FORMAT = "$c[Helpop] $6{player} $e$l-> $a{message}";
    public static String INFO_HELPOP_SENDED = "$eYour help message was send to moderators";
    public static String INFO_WEATHER_CLEAR = "$eCleared weather";
    public static String INFO_WEATHER_STORM = "$eSet weather to storm";
    public static String ERROR_WEATHER_NOWORLD = "$eSpecific world dosn't exist";
    public static String INFO_FLY_SETOFF = "$eFlight mode desactivated";
    public static String INFO_FLY_SETON = "$eFlight mode activated";
    public static String INFO_FLY_SETONFOR = "$eFlight mode activated for {player}";
    public static String ERROR_FLY_NOTONLINE = "$cError! Specificed player must be online!";
    public static String INFO_TP_PLAYERTOPLAYER = "$eTeleported {player} to {target}";
    public static String INFO_TP_CORDINATES = "$eTeleported to {x} {y} {z}";
    public static String INFO_CHAT_BRODCASTON = "$eCore! Chat is now enabled!";
    public static String INFO_CHAT_BRODCASTOFF = "$eCore! Chat is now disabled!";
    public static String INFO_CHAT_SETOFF = "$e$lYou disabled the chat!";
    public static String INFO_CHAT_SETON = "$e$lYou enabled chat!";
    public static String INFO_CHAT_CLEAR = "$e$lChat was clear by {player}!";
    public static String INFO_GM_CHANGE = "$e$lYour Gamemode has changet to {gamemode}";
    public static String INFO_GM_CHANGEPLAYER = "$e$lPlayer gamemode changed!";
    public static String ERROR_GM_NOTONLINE = "$cError! Specificed player must be online!";
    public static String INFO_SPAWN_DETY = "$eYou will be teleported to the spawn site in 20 seconds!";
    public static String ERROR_SPAWN_ABORT = "$cTeleport aborted!";
    public static String ERROR_BACK_NOHISTORY = "$cSorry, but you haven't any teleport history!";
    public static String INFO_BACK_TELEPORTED = "$eYou have been teleported to the last place you stay";
    public static String INFO_BACK_TPDONE = "$eTeleported!";
    public static String ERROR_SPAWN_PLAYEROFFINLE = "$cError! This player is offinle";
    public static String INFO_SPAWN_PLAYERTELEPORTED = "$aYou have been teleported to spawn!";
    public static String INFO_SPAWN_SET = "$eSpawn localization set!";
    public static String INFO_SPAWN_TELEPORT = "$eTeleported!";
    public static String INFO_TPA_REQUEST = "$eA request for teleportation has been sent to {player}";
    public static String INFO_TPA_TELEPORT = "$eTeleported";
    public static String INFO_TPA_REQUESTPLAYER = "$e{player}he asks for teleportation to you. The request is active for 200 seconds!";
    public static String INFO_TPA_ACCEPT = "$eTeleport accepted";
    public static String INFO_TPAHERE_REQUEST = "$e{player}he asks for teleportation to him. The request is active for 200 seconds!";
    public static String ERROR_TPAHERE_NOPLAYER = "$ePlayer is offinle";
    public static String INFO_TPAHERE_REQUESTPLAYER = "$eTeleport accepted";
    public static String INFO_TPAHERE_ACCEPT = "$eTeleport accepted";
    public static String INFO_TPAHERE_TELEPORT = "$eTeleported";
    public static String INFO_TPAHERE_ACCEPTED = "$eA request for teleportation accepted! Wait 20 seconds for teleportation, but remember, you can not move!";
    public static String ERROR_TPA_NOPLAYER = "$ePlayer is offinle";
    public static String ERROR_TPACCEPT_NOTHINGTOACCEPT = "$eYou have nothing to accept";
    public static String INFO_TPA_ACCEPTED = "$eA request for teleportation accepted! Wait 20 seconds for teleportation, but remember, you can not move!";
    public static String ERROR_TPA_ABORT = "$cTeleport aborted!";
    public static String INFO_COMBAT_BRODCASTLOGOUT = "$6>> {player} logged out during the fight {health}!";
    public static String ERROR_COMBAT_CHESTDISABLED = "$cYou can not open chests during a fight!";
    public static String ERROR_COMBAT_BREAKDISABLED = "$cYou can not destroy blocks during a fight!";
    public static String ERROR_COMBAT_PLACE = "$cYou can not place blocks during a fight!";
    public static String ERROR_COMBAT_COMMANDDISABLED = "$cYou can not use this command during combat!";
    public static String INFO_COMBAT_ACTIONBAR = "$cYou are during the fight {coldown}";
    public static String INFO_COMBAT_ENDEDACTION = "$aYou are not in a fight anymore";
    public static String INFO_COMBAT_CHAT = "$cYou are during the fight";
    public static String INFO_COMBAT_ENDCHAT = "$aYou are not in a fight anymore";
    public static String ERROR_COMBAT_CREATIVE = "$cYou can not beat players in creative mode!";
    public static String INFO_COMBAT_COMMAND = "$3$lCore $6>> $a{time} $ayou can leave {check}";
    public static String INFO_COMBAT_YES = "YES";
    public static String INFO_COMBAT_NO = "NO";
    public static String INFO_STONEGENERATOR_PLACE = "$aYou placed a generator!";
    public static String ERROR_RANDOMTP_NOTLOOKING = "$cYou must look at a button";
    public static String INFO_RANDOMTP_REMOVED = "$aButton removed";
    public static String ERROR_RANDOMTP_NOTREMOVED = "$cThis button don't exist";
    public static String ERROR_RANDOMTP_EXIST = "$cA button on this localization is allredy created";
    public static String INFO_RANDOMTP_CREATED = "$aButton created!";
    public static String INFO_RANDOMTP_TELEPORTED = "$aTeleported to X:{x} Y:{y} Z:{z}";
    public static String ERROR_VANISH_PLAYEROFFINLE = "$cThis player is offinle!";
    public static String INFO_VANISH_VISIBLE = "$aYou are now wizible!";
    public static String INFO_VANISH_VANISHED = "$aYou are now invisible";
    public static String INFO_VANISH_ACTIONBAR = "$a$lVanished";
    public static String INFO_VANISH_INFOJOIN = "$a>> {player} joined in vanish mode";
    public static String INFO_MSG_FORMAT = "$3Me-> {from}: $b{message}";
    public static String INFO_MSG_SPYFORMAT = "$c$lSPY | {from} -> {to}: $b{message}";
    public static String ERROR_MSG_PLAYEROFF = "$aThis player is offilne!";
    public static String INFO_MSG_SPY = "$aSpy mode on!";
    public static String ERROR_MSG_ALREADY = "$aYou are spying already";
    public static String ERROR_MSG_ALREADYNO = "$aYou not spying already";
    public static String INFO_MSG_DISABLE = "$aSpy mode desactivated!";
    public static String ERROR_MSG_NOTHINGTOANSWER = "$aYou have nothing to answer";
    public static String INFO_BACK_WAIT = "$aYou will be teleported in history. Wait 20 secounds.";
    public static String ERROR_BACK_ABORT = "$cTeleport aborted!";
    public static String INFO_BRODCAST_TITLE = "$3$lBrodcast";
    public static String INFO_BRODCAST_CHAT = "$3lBrodcast $6>> $c{message}";
    public static String ERROR_INV_NOONLINE = "$cThis player is offinle";
    public static String INFO_INV_OPENED = "$aYou opened {player} inventory!";
    public static String INFO_INV_ENDERCHEST = "$aYou opened {player} enderchest!";
    public static String INFO_HOME_SET = "$aYou saved a new home named {name}";
    public static String INFO_HOME_SETNONAME = "$aHome set!";
    public static String ERROR_HOME_EXIST = "$cA home with this name already exist!";
    public static String INFO_HOME_DEFAULTNOTEXIST = "$cYou have no home";
    public static String ERROR_HOME_NOTEXIST = "$cA home named {name} not exist!";
    public static String INFO_HOME_TELEPORT = "$aTeleported";
    public static String INFO_HOME_MOREHOMES = "$aYou have more homes to with you want teleport {homes}. Use /home {name}";
    public static String ERROR_HOME_ABORT = "$cYou moved. Teleportation aborted";
    public static String INFO_HOME_TELEPORTING = "$aWait 20 secounds. We will teleport you to yours home";
    public static String INFO_HOME_DELETED = "$aHome deleted!";
    public static String ERROR_HOME_DELHOME = "$cDefault home is not set";
    public static String ERROR_HOME_DELNAMEDHOME = "$cNo home exist by this name";
    public static String ERROR_HOME_CANNOTSET = "$cYou have crossed the number of houses set";
    public static String INFO_EFFECT_DRAGONDYE = "$aYou spawned a enderdragon in dying phase!";
    public static String INFO_WEATHER_SUN = "$aDone! Now in your world is no rain and no storm";
    public static String INFO_TPADENY_DENY = "$aRejected!";
    public static String INFO_TPADENY_REJECTED = "$aYour request for teleportation has been rejected!";
    public static String ERROR_TPADENY_NOTHING = "$cYou have nothing to reject!";
    public static String INFO_SKIN_CHANGED = "$aYour skin changed!";
    public static String INFO_SKIN_CLEAR = "$aReseted skin to default!";
    public static String ERROR_SKIN_NOTPREMIUM = "$cYou must give a premium account's nick to change skin!";
    public static String ERROR_SKIN_ERROR = "$cAn unknown error has occurred!";
    public static String INFO_SPEED_CHANGED = "$aYour speed changed";
    public static String INFO_SPEED_PLAYERCHANGED = "$aPlayer speed changed!";
    public static String ERROR_SPEED_NOPLAYER = "$cPlayer is offinle";
    public static String ERROR_SPEED_VALUE = "$cSpeed value can't be higher than 10";
    public static String ERROR_SPEED_NOTAVALUE = "$cPleace give a value not leater!";
    public static String INFO_WORKBENCH_OPENED = "$aYou opened a workbench!";
    public static String INFO_ANVIL_OPENED = "$aYou opened an anvil";
    public static String INFO_TPHERE_TELEPORTED = "$aPlayer teleported to you";
    public static String ERROR_TPHERE_PLAYEROFFINLE = "$aThis player is offinle";
    public static String INFO_TPALL_TELEPORTED = "$aTeleported all online players to you!";
    public static String INFO_TPTOOGLE_TPDENY = "$cThis player has disabled teleportation!";
    public static String INFO_TPTOGGLE_ENABLED = "$aEnabled tptoggle!";
    public static String INFO_TPTOGGLE_DISABLED = "$aDisabled tptoggle!";
    public static String ERROR_TPTOGGLE_ENABLED = "$aTptoogle is already on!";
    public static String ERROR_TPTOGGLE_DISABLED = "$aTptoggle is already off!";
    public static String INFO_ENCHANT_ENCHANTED = "$aEnchanted!";
    public static String ERROR_ENCHANT_NOTNUMBER = "$cSorry but this is not a number!";
    public static String ERROR_ENCHANT_NOTFOUND = "$cNot found a enchantment by this name";
    public static String ERROR_ENCHANT_UNSAFE = "$cSorry, unsafe enchants are disabled!";
    public static String ERROR_ENCHANT_ITEMAIR = "$cWut? Item in your hand is air";
    public static String INFO_ENCHANT_LIST = "$aList of enchantments: {list}";
    public static String INFO_JUMP_YUP = "$aYup!";
    public static String ERROR_JUMP_AIR = "$cNothing to jump :/";
    public static String INFO_THOR_THORED = "$aPlayer thored";
    public static String INFO_THOR_PLAYERTHORED = "$aYou got thored";
    public static String ERROR_THOR_PLAYEROFFINLE = "$cSpecyficed player is offinle!";
    public static String INFO_PING_FORMAT = "$a{player} ping equals {ping} ms.";
    public static String ERROR_PING_GET = "$cError on getting ping!";
    public static String ERROR_PING_PLAYEROFFINLE = "$cError player is offinle!";
    public static String ERROR_WORLD_NOWORLD = "$cSpecyficed world don't exist!";
    public static String INFO_WORLD_TELEPORTED = "$aTeleported!";
    public static String ERROR_WORLD_PLAYEROFFINLE = "$cSpecyficed player is offinle!";
    public static String INFO_WORLD_PLAYERTELEPORTED = "$aPlayer teleported!";
    public static String INFO_WHITELIST_MOTD = "$aWhitelist motd is: {motd}";
    public static String INFO_WHITELIST_SETMOTD = "$aWhitelist motd set to: {motd}";
    public static String ERROR_WHITELIST_CONTAINS = "$cA player named exacly same is soon in the whitelist!";
    public static String INFO_WHITELIST_ADD = "$aPlayer added to whitelist";
    public static String ERROR_WHITELIST_ON = "$cWhitelist is already enabled!";
    public static String INFO_WHITELIST_ON = "$aEnabled whitelist";
    public static String ERROR_WHITELIST_OFF = "$cWhitelist is already disabled!";
    public static String INFO_WHITELIST_OFF = "$aDisabled whitelist!";
    public static String INFO_GC = "$aServer status: /n $afree memory: {freememory} /n $amax memory: {maxmemory} /n $atotal memory: {totalmemory} /n /n $atps: {tps} /n /n $aloaded chunks: {chunks} /n $aloaded entities: {entities} /n tasks: {tasks}";
    public static String INFO_KIT_SUCCES = "$aUdalo ci sie uzyskac kit {kit}";
    public static String INFO_KIT_GUINAME = "$aKity/Zestawy";
    public static List<String> INFO_KIT_LORE = Arrays.asList("$aDostepny dla ciebie: {availability}", "{availablein}");
    public static String INFO_KIT_YES = "$aTAK";
    public static String INFO_KIT_NO = "$cNIE";
    public static String ERROR_KIT_WAIT = "$aAby uzyskac ten zestaw musisz odczekac {time}";
    public static String ERROR_KIT_NOKIT = "$aNie ma zestawu o takiej nazwie!";
    public static String ERROR_KIT_NOPLAYER = "$ePlayer is offinle";
    public static String INFO_KIT_GRANTED = "$aPrzyznano ci kit {kit}!";
    public static String KIT_NOACCES = "$cBrak dostepu do tego kitu!";

    private static File file = new File(Core.getPlugin().getDataFolder(), "lang.yml");
    private static FileConfiguration c = null;

    public static void loadLang(){
        try{

            if(!file.exists()){
                file.getParentFile().mkdir();
                InputStream is = Core.getPlugin().getResource(file.getName());
                if(is != null){
                    Util.copy(is, file);
                }
            }

            c = YamlConfiguration.loadConfiguration(file);

            for(Field f : Lang.class.getFields()){

                if (c.isSet(prefix + f.getName().toLowerCase().replace("_", ".")))
                    f.set(null, c.get(prefix + f.getName().toLowerCase().replace("_", ".")));

            }



        } catch (Exception e){
            consoleLog.warning(e.getMessage());
        }
    }

    public static void saveLang() {
        try {
            for (Field f : Lang.class.getFields()) {
                c.set(prefix + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            c.save(file);
        } catch (Exception e) {
            consoleLog.warning(e.getMessage());
        }
    }

    public static void reloadLang(){
        loadLang();
        saveLang();
    }
}
