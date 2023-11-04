package com.app.todoapp.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static String formatDate(Date date) {
        LocalDate inputLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        if (inputLocalDate.isEqual(today)) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return "Today, " + timeFormat.format(date);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy, h:mm a", Locale.getDefault());
            return dateFormat.format(date);
        }
    }

    public static LocalDate todayLocalDate() {
        return LocalDate.now();
    }

    public static LocalDate yesterdayLocalDate() {
        return todayLocalDate().minusDays(1);
    }

    public static LocalDate tomorrowLocalDate() {
        return todayLocalDate().plusDays(1);
    }
}
