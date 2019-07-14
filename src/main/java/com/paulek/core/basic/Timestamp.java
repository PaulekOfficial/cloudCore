package com.paulek.core.basic;

public class Timestamp {

    private long endTime;
    private String serviceName;
    private String className;

    public Timestamp(String serviceName, String className, long endTime) {
        this.className = className;
        this.endTime = endTime;
        this.serviceName = serviceName;
    }

    public boolean applicable() {
        if (endTime >= System.currentTimeMillis()) {
            return true;
        }

        return false;
    }

    public String timeLeft() {

        int timeLeft = (int) ((endTime - System.currentTimeMillis()) / 1000L);

        int days = timeLeft / (24 * 60 * 60);

        timeLeft -= days * 24 * 60 * 60;

        int hours = timeLeft / (60 * 60);

        timeLeft -= hours * 60 * 60;

        int minutes = timeLeft / 60;

        timeLeft -= minutes * 60;

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
        time.append(timeLeft);
        time.append("sec");

        return time.toString();
    }


    public long getEndTime() {
        return endTime;
    }


    public String getClassName() {
        return className;
    }

    public String getServiceName() {
        return serviceName;
    }
}
