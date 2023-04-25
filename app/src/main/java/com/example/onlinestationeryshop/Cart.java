package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "cart", indices = {@Index(value = {"goodid"},
        unique = true)})
public class Cart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "goodid")
    int goodid;
    @ColumnInfo(name = "count")
    int count;


    public Cart(int goodid, int count){
        this.goodid = goodid;
        this.count = count;
    }

    public Cart(){

    }

}
