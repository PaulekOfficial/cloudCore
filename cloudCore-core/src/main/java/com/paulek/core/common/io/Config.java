package com.paulek.core.common.io;

import com.google.common.collect.ImmutableMap;
import org.diorite.cfg.annotations.*;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CfgClass(name = "Config")
@CfgDelegateDefault("{new}")
@CfgComment("###########################################################")
@CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
@CfgComment(" |                      Notatki                         | #")
@CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
@CfgComment("###########################################################")
@CfgComment("Jezeli chcesz uzyc znakow specjalnych w tym configu, jak znaki specjalne - TEN PLIK MUSI ZOSTAC ZAPISANY W FORMACIE UTF-8")
@CfgComment("Styl configu jest stylowany na configu Essentials https://github.com/EssentialsX - Poniewaz jest on latwy o obsludze")
@CfgComment("Aby plugin sie zaladowal, trzymaj sie tych zasad:")
@CfgComment("   - Nie uzywaj odstepow stworzonych tabem")
@CfgComment("   - Wciecia sa poprawne")
@CfgComment("   - Zamykaj wszystkie apostrofy")
@CfgComment("   - Kazda wartosc musi zostac zakonczona dwukropkiem")
@CfgComment("Konfiguracja dropow zamieszczona jest w osobnym pliku: drops.yml, natomiast podstawowa konfiguracja zamieszczona jest tutaj")
@CfgComment("Konfiguracja zestawow(kitow) zamieszczona jest w osobnym pliku: kits.yml")
public class Config {


    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                 cloudCore Ogulne                     | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy plugin ma byc wlaczony")
    @CfgName("enabled")
    public boolean enabled = false;

    @CfgComment("Z jakiego rodzaju baz danych ma kozysztac plugin. Dostepne MySQL, SQLite")
    @CfgName("storgae-type")
    @CfgStringStyle(CfgStringStyle.StringStyle.DEFAULT)
    public String storageType = "SQLite";

    @CfgComment("Dane logowania do bazdy danych MySQL")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_QUOTED)
    @CfgName("mysql")
    public Map<String, String> mysql = ImmutableMap.<String, String>builder()
            .put("host", "localhost")
            .put("port", "3306")
            .put("user", "root")
            .put("password", "password")
            .put("database-name", "core")
            .build();

    @CfgComment("Jakiego znaku uzywac do tlumaczenia kolorow")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgName("color-mark")
    public static String COLOR_MARK = "$";

    @CfgComment("Maklymalna liczba znakow nicku gracza")
    @CfgName("max-nick-length")
    public int maxNickLenght = 15;

    @CfgComment("Czy zezwalac na zmiane nicku")
    @CfgName("can-change-displayname")
    public boolean canChangeDisplayname = true;

    @CfgComment("Czy zmieniony nick ma rowniez zostac zmieniony na liscie graczy")
    @CfgName("change-playerlist-displayname")
    public boolean changePlayerlistDisplayname = false;

    @CfgComment("Czy zezwalac na wieksze poziomy enchantu, niz naturalnie")
    @CfgName("unsafe-enchantments")
    public boolean unsafeEnchantments = true;

    @CfgComment("Czy opcja godmode ma byc wlaczona")
    @CfgName("godmode")
    public boolean godmode = true;

    @CfgComment("Czy usunac godmode z gracza po wyjsciu z serwera")
    @CfgName("remove-godmode-on-disconect")
    public boolean removeGodmodeOnDisconect = false;

    @CfgComment("W jakich swiatach godmode ma byc niedostepny")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("godmode-id-world-disabled")
    public List<String> disabledWorldsForGodmode = Collections.singletonList("testWorld");

    @CfgComment("Widomosc przy wejsciu gracza, uzyj {USERNAME} aby uzyc zemienionego nicku albo {PLAYER} aby uzyskac nick gracza.")
    @CfgName("custom-join-message")
    public String customJoinMessage = "none";

    @CfgComment("Widomosc przy wyjsciu gracza, uzyj {USERNAME} aby uzyc zemienionego nicku albo {PLAYER} aby uzyskac nick gracza.")
    @CfgName("custom-quit-message")
    public String customQuitMessage = "none";

    @CfgComment("Czy teleportowani gracze, maja telefortowac sie \"na bezpieczne miejsce\"")
    @CfgComment("Plugin oblicza najblizszy klocek w gore i dul, ktory nie jest powietrzem")
    @CfgName("teleport-safety")
    public boolean teleportSafety = true;

    @CfgComment("Czy woda ma zaliczac sie do bezpiecznych klockow")
    @CfgName("teleport-safety-water")
    public boolean teleportSafetyWater = false;

    @CfgComment("Jaki czas trzeba odczekac w momecie teleportacji dla komend /home /tpa /back (podana w sekundach)")
    @CfgName("teleport-delay")
    public int teleportDelay = 20;

    @CfgComment("Jaki czas trzeba odczekac w momencie teleportacji na spawna (podana w sekundach)")
    @CfgName("spawn-delay")
    public int spawnDelay = 20;

    @CfgComment("Czy komendy /tpa, /tpahere, /tpaccept, /tpadeny maja byc wlaczone")
    @CfgName("tpa-enabled")
    public boolean tpaEnabled = true;

    @CfgComment("Po jakim czasie prozba o teleportacje ma zostac automatycznie odzrucona (podana w sekundach)")
    @CfgName("tpa-reject-time")
    public int tpaRejectTime = 200;

    @CfgComment("Maksymalna liczba domow dopuszczalna dla okreslonej grupy")
    @CfgName("homes-amount")
    public Map<String, Integer> homesAmount = Collections.singletonMap("default", 1);

    @CfgComment("Czy easter egg z wybuchajaca owca ma zostac wylczony")
    @CfgName("easteregg")
    public boolean easteregg = true;

    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                     Combatlog                        | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy system anty-wylogowywania podczas walki sie ma byc aktywny")
    @CfgName("combatlog-enabled")
    public boolean combatlogEnabled = true;

    @CfgComment("Czas po zadaniu lub otrzymaniu obrazen od gracza po ktorym mozna sie wylogowac (podany w sekundach)")
    @CfgName("combat-time")
    public int combatTime = 20;

    @CfgComment("Czy globalnie ma zostac wyswietlona wiadomosc o wylogowaniu sie gracza podczas walki")
    @CfgName("combat-announcement")
    public boolean combatAnnouncement = true;

    @CfgComment("Czy wiadomosci o walce maja zostac wyslane go gracza na czcie")
    @CfgName("combat-chat")
    public boolean combatChat = true;

    @CfgComment("Czy na pasku informacyjnym ma zostac wyslana informacja o walce")
    @CfgName("combat-actionbar")
    public boolean combatActionbar = true;

    @CfgComment("Czy obrazenia mobow maja zaliczac sie do walki")
    @CfgName("combat-mob")
    public boolean combatMob = true;

    @CfgComment("Czy po wylogowaniu gracza podaczas walki zabic go")
    @CfgName("combat-kill-on-quit")
    public boolean combatKillOnQuit = true;

    @CfgComment("Czy podczas walki mozna otwierac skrzynie")
    @CfgName("combat-allow-chests")
    public boolean combatAllowChests = false;

    @CfgComment("Czy podczas walki mozna niszczyc bloki")
    @CfgName("combat-allow-block-break")
    public boolean combatAllowBlockBreak = false;

    @CfgComment("Czy podczas walki mozna stawaic bloki")
    @CfgName("combat-allow-block-place")
    public boolean combatAllowBlockPlace = false;

    @CfgComment("Bloki ktore mozna postawic jezeli obcja combat-allow-block-place jest wylaczona")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("combat-blocks-allowed")
    public List<String> combatBlocksAllowed = Arrays.asList("COBBLESTONE", "STONE");

    @CfgComment("Czy podczas walki gracz moze wykonywac komendy")
    @CfgName("combat-allow-commands")
    public boolean combatAllowCommands = false;

    @CfgComment("Komedy ktore mozna wykonywac podczas walki jezeli obcja combat-allow-commands jest wylaczona")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("combat-commands-allowed")
    public List<String> combatCommandsAllowed = Arrays.asList("walka", "help", "combat", "clantilogout");

    @CfgComment("Czy gracze w trybie kreatywnym moga bic graczy na trybie przetrwania")
    @CfgName("combat-allow-creative")
    public boolean combatAllowCreative = true;

    @CfgComment("Czy podczas walki gracz po przekroczeniu bloku ma zostac odrzucony")
    @CfgName("comabt-rejected")
    public boolean comabtRejected = true;

    @CfgComment("Material po ktorym przekroczeniu gracz ma zostac odrzucony")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgName("comabt-rejected-block")
    public String comabtRejectedBlock = "REDSTONE_BLOCK";

    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                        Chat                          | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy modul kontroli czatu ma byc wlaczony")
    @CfgName("chat-module-enabled")
    public boolean chatModuleEnabled = true;

    @CfgComment("Czas ktory trzeba odczekac zanim sie cos napisze (ustaw na 0 aby usunac) (podana w sekundach)")
    @CfgName("chat-slowdown")
    public int chatSlowdown = 5;

    @CfgComment("Czy gracze moga pisac duzymi literami")
    @CfgName("chat-allow-uppercase")
    public boolean chatAllowUppercase = false;

    @CfgComment("Jak formatowany ma byc czat da poszczegulnych grup")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public Map<String, String> chatFormating = ImmutableMap.<String, String>builder()
            .put("default", "$a{displayname} $6» $f{message}")
            .put("admin", "$c{displayname} $6» $c{message}")
            .build();

    @CfgComment("Czy cenzor przeklenstw ma zostac wlaczony")
    @CfgName("chat-cenzor")
    public boolean chatCenzor = true;

    @CfgComment("Na co zamienic slowo ktore jest cenzurowane")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgName("chat-cenzor-replace")
    public String chatCenzorReplace = "xxx";

    @CfgComment("Lisa zakazanych slow przez cenzora - slowo podane na liscie napisane na czacie zostanie zmienione")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("chat-cenzored-list")
    public List<String> chatCenzoredList = Arrays.asList("dupa", "cycki", "chuj", "huj");

    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                       Skins                          | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy modul skinow ma zostac wlaczony")
    @CfgName("skins-enabled")
    public boolean skinsEnabled = true;

    @CfgComment("Czy zezwolic na samodzielna zmiane skinow /skin")
    @CfgName("skins-command")
    public boolean skinsCommand = true;

    @CfgComment("Po jakim czasie skin gracza ma zostac odswierzony na nowy (podana w sekundach)")
    @CfgName("skins-refreshment")
    public int skinsRefreshment = 1296000;

    @CfgComment("Czy gracze non-premium maja dostac losowego skina zawartego z listy")
    @CfgName("skins-non-premium")
    public boolean skinsNonPremium = true;

    @CfgComment("Lista nickow premium ktore beda wykorzystywane do przydzielania graczom non-premium")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("skins-list")
    public List<String> skinsList = Arrays.asList("skkf", "viv0", "set", "paulekofficial", "dbanaszewski");

    @CfgComment("Czy po zebraniu x skinow premium do bazy danych losowac skina z bazy - lista skins-list nie bedzie wtedy obowiazywala")
    @CfgName("skins-override")
    public boolean skinsOverride = true;

    @CfgComment("Po jakiej ilosci zebranych skinow w bazie danych, losowac skiny z bazy, dziala jezeli wylaczyles/as opcje skins-override")
    @CfgName("skins-override-value")
    public int skinsOverrideValue = 60;

    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                  Random Teleport                     | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy modul losowej teleportacj ma byc wlaczony")
    @CfgName("rtp-enabled")
    public boolean rtpEnabled = true;

    @CfgComment("Lokalizacja centralna osi x, od ktorej ma byc losowana losowa teleportacja")
    @CfgName("rtp-center-x")
    public int rtpCenterX = 0;

    @CfgComment("Lokalizacja centralna osi z, od ktorej ma byc losowana losowa teleportacja")
    @CfgName("rtp-center-z")
    public int rtpCenterZ = 0;

    @CfgComment("Maksymalna liczba kratek w osi x, na ktura losowa teleportacja moze cie wyrzucic")
    @CfgName("rtp-max-x")
    public int rtpMaxX = 5000;

    @CfgComment("Maksymalna liczba kratek w osi z, na ktura losowa teleportacja moze cie wyrzucic")
    @CfgName("rtp-max-z")
    public int rtpMaxZ = 5000;

    @CfgComment("Czarna lista biomow na ktorych nie moze zostac wylosowana losowa lokalizacja")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("rtp-bioms-black-list")
    public List<String> rtpBiomsBlackList = Arrays.asList("OCEAN", "DEEP_OCEAN");

    @CfgComment("Czy gracz po pierwszym wejsciu na serwer ma zostac losowo przeteleportowany na mapie")
    @CfgName("rtp-on-first-join")
    public boolean rtpOnFirstJoin = true;

    @CfgComment("###########################################################")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment(" |                  Stone Generator                     | #")
    @CfgComment(" §~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~§ #")
    @CfgComment("###########################################################")

    @CfgComment("Czy modul stone generator ma zostac wlaczony")
    @CfgName("generator-enabled")
    public boolean generatorEnabled = true;

    @CfgComment("Czy generowac stone po postawieniu na generator bloku stone")
    @CfgName("generator-stone")
    public boolean generatorStone = true;

    @CfgComment("Czy obsidian stone po postawieniu na generator bloku obsidian")
    @CfgName("generator-obsidian")
    public boolean generatorObsidian = true;

    @CfgComment("Czy nadpisac funkcjonalnosc generatora i generowac stone odrazu po postawieniu bloku")
    @CfgName("generator-override")
    public boolean generatorOverride = true;

    @CfgComment("Co ile sekund ma regenerowac sie blok")
    @CfgName("generator-delay")
    public int generatorDelay = 1;

    @CfgComment("Material klocka ktory zostanie wykorzystany jako generator")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgName("generator-block")
    public String generatorBlock = "END_STONE";

    @CfgComment("Crafting generatora, rozpiska wygladu craftingu nadole")
    @CfgComment("         【1】【2】【3】")
    @CfgComment("         【4】【5】【6】")
    @CfgComment("         【7】【8】【9】")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("generator-crafting")
    public Map<Integer, String> generatorCrafting = ImmutableMap.<Integer, String>builder()
            .put(1, "REDSTONE")
            .put(2, "IRON_INGOT")
            .put(3, "REDSTONE")
            .put(4, "IRON_INGOT")
            .put(5, "STONE")
            .put(6, "IRON_INGOT")
            .put(7, "REDSTONE")
            .put(8, "PISTON")
            .put(9, "REDSTONE")
            .build();

    @CfgComment("Nazwa generatora po scraftowaniu go")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgName("genertor-name")
    public String genertorName = "$cGenerator stone";

    @CfgComment("Opis generatora po najechaniu na scraftowany blok")
    @CfgStringStyle(CfgStringStyle.StringStyle.ALWAYS_SINGLE_QUOTED)
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    @CfgName("generator-lore")
    public List<String> generatorLore = Arrays.asList("$aPostaw ten blok generatora,", "$aPostaw na nim stone lub obsidian", "$aw zaleznosci co na generowac.", "$aCiesz sie twoim generatorem :D");

//    public static boolean WHITELIST_ENABLE = true;
//    public static String WHITELIST_MOD = "$cYou are not in whitelist!";
//    public static List<String> WHITELIST_ALLOWEDPLAYERS = Arrays.asList();
//    public static String DROP_GUINAME = "$cDropy";
//    public static String DROP_ITEM_NAME = "$a{drop-name}";
//    public static List<String> DROP_ITEM_LORE = Arrays.asList("Mozna wydropic: {can-drop}", "szansa: {chance}", "exp: {exp}", "dziala fortunka: {enabled-fortune}", "wypada na wysokosci: {height}", "Moze wypasc w ilosci {amount}", "mozna wydobyc dzieki: {tools}");
//    public static boolean DROP_ONGAMEMODE = false;
//    public static boolean DROP_VIP_ENABLE = true;
//    public static double DROP_VIP_MULTIPLIER = 1.03;
//    public static boolean DROP_HARDCORE = true;
//    public static double DROP_FORTUNE_1_CHANCE = 20.91;
//    public static String DROP_FORTUNE_1_AMOUNT = "1-2";
//    public static double DROP_FORTUNE_2_CHANCE = 10.91;
//    public static String DROP_FORTUNE_2_AMOUNT = "2-3";
//    public static double DROP_FORTUNE_3_CHANCE = 2.91;
//    public static String DROP_FORTUNE_3_AMOUNT = "3-4";

}
