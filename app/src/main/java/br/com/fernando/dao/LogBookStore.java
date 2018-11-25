package br.com.fernando.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.com.fernando.converters.DateConverter;
import br.com.fernando.model.Memory;

@Database(entities = Memory.class, version = 1)
@TypeConverters({ DateConverter.class })
public abstract class LogBookStore extends RoomDatabase {

    private static LogBookStore instance = null;

    public abstract MemoryDao getMemoryDao();

    public static LogBookStore getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    LogBookStore.class,
                    "logbook.db")
                    .build();
        }
        return instance;
    }

}
