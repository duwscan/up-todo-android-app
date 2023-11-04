package com.app.todoapp.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    public static LocalTime fromString(String value) {
        return value == null ? null : LocalTime.parse(value);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime time) {
        return time == null ? null : time.toString();
    }
}
