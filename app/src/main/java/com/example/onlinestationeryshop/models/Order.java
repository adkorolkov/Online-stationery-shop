package com.example.onlinestationeryshop.models;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
}
