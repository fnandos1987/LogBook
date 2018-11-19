package br.com.fernando.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timeInMillis) {
        if(timeInMillis == null){
            return null;
        }

        return new Date(timeInMillis);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        if(date == null){
            return null;
        }

        return date.getTime();
    }

}
