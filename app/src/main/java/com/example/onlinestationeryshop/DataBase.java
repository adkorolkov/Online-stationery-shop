package com.example.onlinestationeryshop;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Config.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ConfigDao configDao();
}
