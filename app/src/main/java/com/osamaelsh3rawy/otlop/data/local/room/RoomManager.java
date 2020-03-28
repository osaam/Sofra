package com.osamaelsh3rawy.otlop.data.local.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;

@Database(entities = {ItemsData.class}, version = 3, exportSchema = false)
public abstract class RoomManager extends RoomDatabase {

    private static RoomManager roomManager;

    public abstract RoomDao roomDao();

    public static synchronized RoomManager getInstance(Context context) {
        if (roomManager == null) {
            roomManager = Room.databaseBuilder(context.getApplicationContext(), RoomManager.class, "sofra_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return roomManager;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
