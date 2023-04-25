package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "config", indices = {@Index(value = {"name"},
        unique = true)})
public class Config {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "value")
    String value;

    public Config(String name, String value){
        this.name = name;
        this.value = value;
    }

    public Config() {
    }
}
