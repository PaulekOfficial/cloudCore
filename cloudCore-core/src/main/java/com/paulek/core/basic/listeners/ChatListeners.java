package com.paulek.core.basic.listeners;

import com.paulek.core.Core;
import common.ColorUtil;
import common.io.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatListeners implements Listener {

    private Core core;

    private List<String> cenzored;
    private String replacecenzor;
    private HashMap<UUID, Long> slowdon;
    private HashMap<String, String> groups_format;

    public ChatListeners(Core core){
        this.core = Objects.requireNonNull(core, "Core");
        cenzored = core.getConfiguration().chatCenzoredList;
        replacecenzor = core.getConfiguration().chatCenzorReplace;
        slowdon = new HashMap<UUID, Long>();
        groups_format = new HashMap<String, String>();
    }

    @EventHandler
    public void onPlayerType(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        for (String s : groups_format.keySet()) {
            if (core.getPermission().playerInGroup(event.getPlayer(), s)) {
                String format;
                if (core.getChat().getPrimaryGroup(event.getPlayer()) != null) {
                    if (!event.getPlayer().hasPermission("core.chat.color")) {
                        format = ColorUtil.fixColor(groups_format.get(s).replace("{displayname}", core.getChat().getGroupPrefix(event.getPlayer().getWorld(), core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName())).replace("{message}", event.getMessage());
                    } else {
                        format = ColorUtil.fixColor(groups_format.get(s).replace("{displayname}", core.getChat().getGroupPrefix(event.getPlayer().getWorld(), core.getPermission().getPrimaryGroup(event.getPlayer())) + event.getPlayer().getDisplayName()).replace("{message}", event.getMessage()));
                    }
                    event.setFormat(format);
                }
            }
        }

        if (!core.isChatEnabled()) {
            if (!event.getPlayer().hasPermission("core.chat.writewhendisabled")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }
        if (!core.getConfiguration().chatModuleEnabled) {
            if (!event.getPlayer().hasPermission("core.chat.disable")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_DISABLEDTYPE));
                return;
            }
        }

        if (core.getConfiguration().chatAllowUppercase) {
            if (!event.getPlayer().hasPermission("core.chat.nouppercase")) {
                event.setMessage(event.getMessage().toLowerCase());
            }
        }

        if (core.getConfiguration().chatSlowdown > 0) {
            if (!event.getPlayer().hasPermission("core.chat.noslowdown")) {
                UUID uuid = event.getPlayer().getUniqueId();
                if (slowdon.containsKey(uuid)) {
                    if (((System.currentTimeMillis() - slowdon.get(uuid)) / 1000) < core.getConfiguration().chatSlowdown) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ColorUtil.fixColor(Lang.ERROR_CHAT_SLOWDOWN));
                    } else {
                        slowdon.put(uuid, System.currentTimeMillis());
                    }
                } else {
                    slowdon.put(uuid, System.currentTimeMillis());
                }
            }
        }
        if (core.getConfiguration().chatCenzor) {
            if (!event.getPlayer().hasPermission("core.chat.nocenzor")) {
                for (String s : cenzored) {
                    event.setMessage(event.getMessage().replaceAll(s, replacecenzor));
                }
            }
        }
    }

    public void loadGroups() {

        for (String s : core.getConfiguration().chatFormating.keySet()) {

            String format = core.getConfiguration().chatFormating.get(s);

            groups_format.put(s, format);

        }

    }

}
