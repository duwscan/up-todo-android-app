package com.app.todoapp.utils;

import java.util.Locale;

public class TimePickerValue {
    int hours;
    int minutes;
    int seconds;

    public int getHours() {
        return hours;
    }

    public Long toMillis() {
        long totalMillis = hours * 60 * 60 * 1000L + minutes * 60 * 1000L + seconds * 1000L;
        return totalMillis;
    }

    public String formatTimeWithHours() {
        int hours = getHours();
        int minutes = getMinutes();
        int seconds = getSeconds();
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static TimePickerValue fromMillis(long durationMillis) {
        int hours = (int) (durationMillis / (1000 * 60 * 60));
        int minutes = (int) ((durationMillis / (1000 * 60)) % 60);
        int seconds = (int) ((durationMillis / 1000) % 60);
        return new TimePickerValue(hours, minutes, seconds);
    }

    @Override
    public String toString() {
        return "TimePickerValue{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public TimePickerValue() {
    }

    public TimePickerValue(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public boolean isValidate() {
        if (hours == 0 && minutes == 0 && seconds == 0) {
            return false;
        }
        return true;
    }
}
