package com.example.onlinestationeryshop.models;

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
    public String value;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Config(String name, String value){
        this.name = name;
        this.value = value;
    }

    public Config() {
    }
}
