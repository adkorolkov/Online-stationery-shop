package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;


@Entity(tableName = "SearchHistory")
public class History {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "quer")
    String quer;
    @ColumnInfo(name = "time")
    String time;


    public String getQuer() {
        return quer;
    }

    public History(String quer, String time){
        this.quer = quer;
        this.time = time;
    }

    public History(){
    }
}
