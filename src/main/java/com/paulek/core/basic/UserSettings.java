package com.paulek.core.basic;

public class UserSettings {

    private boolean socialspy;
    private boolean vanish;
    private boolean tptoogle;
    private boolean tps;

    public UserSettings(){
        this(false, false, false, false);
    }

    public UserSettings(boolean socialspy, boolean vanish, boolean tptoogle, boolean tps){
        this.socialspy = socialspy;
        this.vanish = vanish;
        this.tptoogle = tptoogle;
        this.tps = tps;
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
