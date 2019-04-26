package com.paulek.core.basic;

import java.util.HashMap;
import java.util.Map;

public class UserSettings {

    private boolean socialspy;
    private boolean vanish;
    private boolean tptoogle;
    private boolean tps;
    private Map<String, Boolean> drops;

    public UserSettings(){
        this(false, false, false, false, new HashMap<>());
    }

    public UserSettings(boolean socialspy, boolean vanish, boolean tptoogle, boolean tps, Map<String, Boolean> drops){
        this.socialspy = socialspy;
        this.vanish = vanish;
        this.tptoogle = tptoogle;
        this.tps = tps;
        this.drops = drops;
    }

    public boolean canDrop(String name){
        return (drops.get(name) == null) ? true : drops.get(name);
    }

    public void setDrop(String name, boolean value){
        drops.put(name, value);
    }

    public boolean isSocialspy() {
        return socialspy;
    }

    public void setSocialspy(boolean socialspy) {
        this.socialspy = socialspy;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean isTptoogle() {
        return tptoogle;
    }

    public void setTptoogle(boolean tptoogle) {
        this.tptoogle = tptoogle;
    }

    public boolean isTps() {
        return tps;
    }

    public void setTps(boolean tps) {
        this.tps = tps;
    }

}
