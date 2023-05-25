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
    @ColumnInfo(name = "goodname")
    String goodname;
    @ColumnInfo(name = "price")
    int price;


    public Cart(int goodid, int count, String goodname, int price){
        this.goodid = goodid;
        this.count = count;
        this.goodname = goodname;
        this.price = price;
    }

    public Cart(){

    }

}
