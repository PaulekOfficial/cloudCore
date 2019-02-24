package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import com.paulek.core.basic.ChatManager;
import com.paulek.core.basic.data.CombatStorage;
import com.paulek.core.basic.data.TpaStorage;
import com.paulek.core.commands.cmds.admin.VanishCMD;
import com.paulek.core.commands.cmds.user.SpawnCMD;
import com.paulek.core.commands.cmds.user.TpacceptCMD;
import com.paulek.core.common.Util;
import com.paulek.core.common.configs.Config;
import com.paulek.core.common.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PluginListeners implements Listener {

    private List<String> cenzored = Config.BLACKLISTED_WORDS;
    private String replacecenzor = Config.CHAT_CENZOREDREPLACE;
    private HashMap<UUID, Long> slowdon = new HashMap<UUID, Long>();
    private static HashMap<String, String> groups_format = new HashMap<String, String>();

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event){

        UUID uuid = event.getPlayer().getUniqueId();

        if(VanishCMD.getList().containsKey(uuid)){

            BukkitTask id = VanishCMD.getList().get(uuid);

            Bukkit.getScheduler().cancelTask(id.getTaskId());

            VanishCMD.getList().remove(uuid);

        }

        if(TpacceptCMD.getTo_teleport().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(TpacceptCMD.getTo_teleport().get(uuid));
            TpacceptCMD.getTo_teleport().remove(uuid);
        }

        if(TpaStorage.getToAcceptTpahere(uuid) != null){
            TpaStorage.removeToAcceptTpahere(uuid);
            TpaStorage.cancelTaskTpahere(uuid);
        }

        if(TpaStorage.getToAcceptTpa(uuid) != null){
            TpaStorage.removeToAcceptTpa(uuid);
            TpaStorage.cancelTaskTpa(uuid);
        }

        if(SpawnCMD.getIn_detly().containsKey(uuid)){
            Bukkit.getScheduler().cancelTask(SpawnCMD.getIn_detly().get(uuid));
            SpawnCMD.getIn_detly().remove(uuid);
        }

        if(CombatStorage.isMarked(uuid)){
            CombatStorage.unmark(uuid);

            if(Config.SETTINGS_COMBAT_BRODCASTLOGOUT) {

                String s = Util.fixColor(Lang.INFO_COMBAT_BRODCASTLOGOUT).replace("{player}", event.getPlayer().getName()).replace("{health}", Double.toString((int)event.getPlayer().getHealth()) + "♥");

                Bukkit.broadcastMessage(s);

            }

            if(Config.SETTINGS_COMBAT_KILLONLOGOUT) event.getPlayer().setHealth(0.0);

        }
    }

    @EventHandler
    public void onPreLogin(org.bukkit.event.player.AsyncPlayerPreLoginEvent event){

        if(Config.ENABLE_WHITELIST){

            if(Config.SETTINGS_WHITELIST_ENABLED){

                if(!Config.SETTINGS_WHITELIST_LIST.contains(event.getName())){

                    event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Util.fixColor(Config.SETTINGS_WHITELIST_MOTD));

                }

            }

        }

    }

    @EventHandler
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event){

        for(String s : groups_format.keySet()){
            if(Core.getPermission().playerInGroup(event.getPlayer(), s)){
                String format;
                if(Core.getChat().getPrimaryGroup(event.getPlayer()) != null) {
                    if (!event.getPlayer().hasPermission("core.chat.color")) {
                        format = Util.fixColor(groups_format.get(s).replace("{displayname}", Core.getChat().getGroupPrefix(event.getPlayer().getWorld(), Core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName())).replace("{message}", event.getMessage());
                    } else {
                        format = Util.fixColor(groups_format.get(s).replace("{displayname}", Core.getChat().getGroupPrefix(event.getPlayer().getWorld(), Core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName()).replace("{message}", event.getMessage()));
                    }
                    event.setFormat(format);
                }
            }
        }

        if(!ChatManager.isChatEnabled()){
            if(!event.getPlayer().hasPermission("core.chat.writewhendisabled")){
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }
        if(!Config.ENABLE_CHAT){
            if(!event.getPlayer().hasPermission("core.chat.disable")){
                event.setCancelled(true);
                event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }

        if(Config.ENABLE_NOUPPERCASE){
            if(!event.getPlayer().hasPermission("core.chat.nouppercase")){
                event.setMessage(event.getMessage().toLowerCase());
            }
        }

        if(Config.ENABLE_SLOWDOWN) {
            if(!event.getPlayer().hasPermission("core.chat.noslowdown")){
                UUID uuid = event.getPlayer().getUniqueId();
                if (slowdon.containsKey(uuid)) {
                    if (((System.currentTimeMillis() - slowdon.get(uuid)) / 1000) < Config.CHAT_SLOWDOWN) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_CHAT_SLOWDOWN));
                    } else {
                        slowdon.put(uuid, System.currentTimeMillis());
                    }
                } else {
                    slowdon.put(uuid, System.currentTimeMillis());
                }
            }
        }
        if(Config.ENABLE_CENZOR){
            if(!event.getPlayer().hasPermission("core.chat.nocenzor")){
                for (String s : cenzored){
                    event.setMessage(event.getMessage().replaceAll(s, replacecenzor));
                }
            }
        }
    }

    public static void loadGroups(){

        for(String s : Config.CHAT_FORMATING){

            String[] a = s.split(" ");

            groups_format.put(a[0], s.replaceAll(a[0], "").replaceFirst(" ", ""));

        }

    }

}
