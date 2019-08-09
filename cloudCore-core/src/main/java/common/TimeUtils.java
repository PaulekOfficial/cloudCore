package common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static long periodStringToLong(String string){

        if(string.contains("s")){

        } else if(string.contains("m")){
            return Long.parseLong(string.replace("m", "")) * 60;
        } else if(string.contains("h")){
            return Long.parseLong(string.replace("h", "")) * 3600;
        } else if (string.contains("d")){
            return Long.parseLong(string.replace("d", "")) * 86400;
        } else if(string.contains("M")){
            return Long.parseLong(string.replace("M", "")) * 2592000;
        } else if(string.contains("y")){
            return Long.parseLong(string.replace("y", "")) * 31104000;
        }

        return 0L;
    }

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
