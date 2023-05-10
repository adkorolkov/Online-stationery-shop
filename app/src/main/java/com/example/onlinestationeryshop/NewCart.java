package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart", indices = {@Index(value = {"goodid"},
        unique = true)})
public class NewCart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "goodid")
    int goodid;
    @ColumnInfo(name = "count")
    int count;

    Good good;


    public NewCart(int goodid, int count, Good good){
        this.goodid = goodid;
        this.count = count;
        this.good = good;
    }

    public NewCart(){

    }

}
