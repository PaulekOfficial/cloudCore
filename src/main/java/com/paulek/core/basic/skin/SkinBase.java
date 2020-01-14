package com.paulek.core.basic.skin;

import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public interface SkinBase {

    void applySkinForPlayers(Player player);

    void updateSkinForPlayer(Player player);

    Property getProperty();

    String getName();

    void setName(String name);

    String getValue();

    String getSignature();

    LocalDateTime getLastUpdate();

    void setLastUpdate(LocalDateTime lastUpdate);

    boolean isManuallySet();

}
