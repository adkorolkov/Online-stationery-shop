package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "ordertable")
public class Order {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "status")
    String status;
    @ColumnInfo(name = "time")
    String time;


    public Order(String status, String time){
        this.status = status;
        this.time = time;
    }

    public Order(){

    }
}
