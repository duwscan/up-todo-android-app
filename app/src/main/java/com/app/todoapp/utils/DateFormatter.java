package com.app.todoapp.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

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
}
