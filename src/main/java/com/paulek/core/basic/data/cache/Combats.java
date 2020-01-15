package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.event.PlayerCombatEndEvent;
import com.paulek.core.common.ActionBarUtil;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Combats implements Cache<LocalDateTime, UUID> {

    private Core core;
    private Map<UUID, LocalDateTime> cachedCombats = new ConcurrentHashMap<>(100);

    public Combats(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(core, run -> {
            Iterator<UUID> i = cachedCombats.keySet().iterator();

            while (i.hasNext()) {

                UUID uuid = i.next();
                LocalDateTime time = cachedCombats.get(uuid);

                long coldDown = core.getConfiguration().combatTime - time.until(LocalDateTime.now(), ChronoUnit.SECONDS);

                String a = ColorUtil.fixColor(Lang.INFO_COMBAT_ACTIONBAR).replace("{coldown}", Long.toString(coldDown));

                Player player = Bukkit.getPlayer(uuid);
                if (player != null && core.getConfiguration().combatActionbar){
                    ActionBarUtil.sendActionbar(player, a);
                }

                if (coldDown <= 0) {
                    i.remove();

                    if (player != null){
                        ActionBarUtil.sendActionbar(player, ColorUtil.fixColor(Lang.INFO_COMBAT_ENDEDACTION));
                        if (core.getConfiguration().combatChat) player.sendMessage(ColorUtil.fixColor(Lang.INFO_COMBAT_ENDCHAT));
                        Bukkit.getPluginManager().callEvent(new PlayerCombatEndEvent(player));
                    }

                }

            }
        }, 0, 20 * 60);
    }

    @Override
    public LocalDateTime get(UUID uuid) {
        return cachedCombats.get(uuid);
    }

    @Override
    public void add(UUID uuid, LocalDateTime localDateTime) {
        cachedCombats.put(uuid, localDateTime);
    }

    @Override
    public void delete(UUID uuid) {
        cachedCombats.remove(uuid);
    }
}
