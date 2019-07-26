package com.paulek.core.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeUtils {

    public static Integer compareLocalDateTimeInSecounds(LocalDateTime fromDate, LocalDateTime toDate){

        LocalDateTime tempDate = LocalDateTime.from(fromDate);

        long years = tempDate.until(toDate, ChronoUnit.YEARS);
        tempDate = tempDate.plusYears(years);

        long months = tempDate.until(toDate, ChronoUnit.MONTHS);
        tempDate = tempDate.plusMonths(months);

        long days = tempDate.until(toDate, ChronoUnit.DAYS);
        tempDate = tempDate.plusDays(days);

        long hours = tempDate.until(toDate, ChronoUnit.HOURS);
        tempDate = tempDate.plusHours(hours);

        long minutes = tempDate.until(toDate, ChronoUnit.MINUTES);
        tempDate = tempDate.plusMinutes(minutes);

        long seconds = tempDate.until(tempDate, ChronoUnit.SECONDS);

        int overal = 0;

        overal += years * 12 * 30 * 12 * 60 * 60;
        overal += months * 30 * 12 * 60 * 60;
        overal += days * 12 * 60 * 60;
        overal += minutes * 60;
        overal += seconds;

        return overal;
    }

}
