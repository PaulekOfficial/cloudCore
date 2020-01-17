package com.paulek.core.basic.data.cache;

import com.paulek.core.Core;
import com.paulek.core.basic.data.Cache;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.DataModel;
import com.paulek.core.basic.data.cache.models.mysql.MySQLSkinsData;
import com.paulek.core.basic.skin.Skin;
import com.paulek.core.common.MojangApiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Skins implements Cache<Skin, UUID> {

    private Core core;
    private Data<Skin, UUID> skinsData;
    private DataModel dataModel;
    private Map<UUID, Skin> cachedSkins = new ConcurrentHashMap<>(100);

    public Skins(Core core, DataModel dataModel) {
        this.dataModel = dataModel;
        this.core = Objects.requireNonNull(core, "core");
    }

    public void init() {
        switch (dataModel) {
            case MYSQL: skinsData = new MySQLSkinsData(core);
            case SQLITE: skinsData = null;
            case FLAT: skinsData = null;
            default: skinsData = new MySQLSkinsData(core);
        }
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
    public Skin get(UUID uuid) {
        if(cachedSkins.containsKey(uuid)) {
            return cachedSkins.get(uuid);
        }
        Player player = Bukkit.getPlayer(uuid);
        Skin skinBase = skinsData.load(uuid);
        if(skinBase != null) {
            if(!skinBase.isManuallySet()) {
                long lastUpdated = skinBase.getLastUpdate().until(LocalDateTime.now(), ChronoUnit.MILLIS);
                if(core.getConfiguration().skinsRefreshment <= lastUpdated && !skinBase.isManuallySet()) {
                    assert player != null;
                    skinBase = get(player.getName());
                    skinBase.setUuid(player.getUniqueId());
                    add(uuid, skinBase);
                    skinsData.save(skinBase);
                }
            }
            add(uuid, skinBase);
        } else {
            if(player != null) {
                skinBase = get(player.getName());
                if(skinBase != null) {
                    skinBase.setUuid(player.getUniqueId());
                    add(uuid, skinBase);
                    save(skinBase);
                } else {
                    if(core.getConfiguration().skinsNonPremium) {
                        int count = skinsData.count();
                        Random random = new Random();
                        if (core.getConfiguration().skinsOverride && count >= core.getConfiguration().skinsOverrideValue) {
                            int id = random.nextInt(count) + 1;
                            skinBase = skinsData.load(id);
                            skinBase.setUuid(player.getUniqueId());
                            add(uuid, skinBase);
                        } else {
                            int id = random.nextInt(core.getConfiguration().skinsList.size());
                            String nick = core.getConfiguration().skinsList.get(id);
                            skinBase = MojangApiUtil.getPremiumSkin(nick, core);
                            if (skinBase == null) {
                                id = random.nextInt(core.getConfiguration().skinsList.size());
                                nick = core.getConfiguration().skinsList.get(id);
                                skinBase = MojangApiUtil.getPremiumSkin(nick, core);
                            }
                            if (skinBase != null) {
                                skinBase.setUuid(player.getUniqueId());
                                add(uuid, skinBase);
                            }
                        }
                    }
                }
            }
        }
        return skinBase;
    }

    public void save(Skin skin) {
        skinsData.save(skin);
    }

    public Skin get(String nick) {
        return MojangApiUtil.getPremiumSkin(nick, core);
    }

    @Override
    public void add(UUID uuid, Skin skin) {
        cachedSkins.put(uuid, skin);
    }

    @Override
    public void delete(UUID uuid) {
        cachedSkins.remove(uuid);
    }
}
