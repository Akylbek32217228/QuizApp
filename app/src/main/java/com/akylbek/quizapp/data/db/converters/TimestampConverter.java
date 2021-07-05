package com.akylbek.quizapp.data.db.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class TimestampConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if (value != null) {
            return new Date(value);
        } else {
            return null;
        }
    }

    @TypeConverter
    public static Long dateToTimestamp(Date value) {
        return value == null ? 0L : value.getTime();
    }
}
