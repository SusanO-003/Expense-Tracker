package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String format(LocalDate date) {
        return date.format(FORMATTER);
    }

    public static LocalDate parse(String dateStr) {
        return LocalDate.parse(dateStr, FORMATTER);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate firstDayOfMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate lastDayOfMonth() {
        return LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    }

    public static LocalDate daysAgo(int days) {
        return LocalDate.now().minusDays(days);
    }

    public static LocalDate daysAhead(int days) {
        return LocalDate.now().plusDays(days);
    }
} 

