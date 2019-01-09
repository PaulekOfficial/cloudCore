package com.paulek.core.data.objects;

public class UserSettings {

    private boolean socialspy;
    private boolean vanish;
    private boolean tptoogle;
    private boolean tps;

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
