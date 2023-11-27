package com.app.todoapp.utils;


import com.app.todoapp.calendar.cmp.model.CallDay;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static List<CallDay> getAllDaysWithDayOfWeekInMonth(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        List<CallDay> allDaysWithDayOfWeek = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            CallDay callDay = new CallDay();
            callDay.dayOfWeek = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            callDay.day = day;
            allDaysWithDayOfWeek.add(callDay);
        }
        return allDaysWithDayOfWeek;
    }
}
