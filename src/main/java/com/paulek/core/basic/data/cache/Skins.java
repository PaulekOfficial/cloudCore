package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.skin.Skin;
import com.paulek.core.basic.skin.SkinBase;
import com.paulek.core.common.MojangApiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Skins implements Cache<SkinBase, UUID> {

    private Core core;
    private Data<SkinBase, UUID> skinsData;
    private DataModel dataModel;
    private Map<UUID, SkinBase> cachedSkins = new ConcurrentHashMap<>(100);

    public Skins(Core core) {
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        skinsData = switch (dataModel) {
            case MYSQL -> null;
            case SQLITE -> null;
            case FLAT -> null;
        };
        assert skinsData != null;
        skinsData.load();
        Bukkit.getScheduler().runTaskTimerAsynchronously(core.getPlugin(), run -> {
            skinsData.save(cachedSkins.values(), false);
        }, 0, 20 * 60);
    }

    public void saveDataBeforeShutdown() {
        skinsData.save(cachedSkins.values(), true);
    }

    @Override
    public SkinBase get(UUID uuid) {
        if(cachedSkins.containsKey(uuid)) {
            return cachedSkins.get(uuid);
        }
        Player player = Bukkit.getPlayer(uuid);
        Skin skinBase = (Skin) skinsData.load(uuid);
        if(skinBase != null) {
            if(!skinBase.isManuallySet()) {
                long lastUpdated = skinBase.getLastUpdate().until(LocalDateTime.now(), ChronoUnit.MILLIS);
                if(core.getConfiguration().skinsRefreshment <= lastUpdated && !skinBase.isManuallySet()) {
                    assert player != null;
                    skinBase = get(player.getName());
                    skinsData.save(skinBase);
                }
            }
            add(uuid, skinBase);
        } else {
            if(player != null) {
                skinBase = get(player.getName());
                if(skinBase != null) {
                    add(uuid, skinBase);
                }
            }
        }
        return skinBase;
    }

    public Skin get(String nick) {
        return MojangApiUtil.getPremiumSkin(nick, core);
    }

    @Override
    public void add(UUID uuid, SkinBase skin) {
        cachedSkins.put(uuid, skin);
    }

    @Override
    public void delete(UUID uuid) {
        cachedSkins.remove(uuid);
    }
}
