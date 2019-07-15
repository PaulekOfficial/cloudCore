package com.paulek.core.basic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Timestamp {

    private UUID uuid;
    private String serviceName;
    private String className;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean dirty;

    public Timestamp(UUID uuid, String serviceName, String className, LocalDateTime startTime, LocalDateTime endTime) {
        this.uuid = uuid;
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        this.serviceName = serviceName;
        this.dirty = false;
    }

    public boolean applicable() {
        return endTime.isAfter(LocalDateTime.now());

    }

    public String timeLeft() {

        LocalDateTime fromTemp = LocalDateTime.from(LocalDateTime.now());
        LocalDateTime toTemp = endTime;

        long days = fromTemp.until(toTemp, ChronoUnit.DAYS);
        fromTemp = fromTemp.plus(days, ChronoUnit.DAYS);

        long hours = fromTemp.until(toTemp, ChronoUnit.HOURS);
        fromTemp = fromTemp.plus(hours, ChronoUnit.HOURS);

        long minutes = fromTemp.until(toTemp, ChronoUnit.MINUTES);
        fromTemp = fromTemp.plus(minutes, ChronoUnit.MINUTES);

        StringBuilder time = new StringBuilder();

        if (days >= 1) {
            time.append(days);
            time.append("d ");
        }
        if (hours >= 1) {
            time.append(hours);
            time.append("h ");
        }
        if (minutes >= 1) {
            time.append(minutes);
            time.append("min ");
        }
        time.append(fromTemp.until(toTemp, ChronoUnit.SECONDS));
        time.append("sec");

        return time.toString();
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getClassName() {
        return className;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
