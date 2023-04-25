package com.example.onlinestationeryshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ordercontent",foreignKeys = {@ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderid"),
        @ForeignKey(entity = Good.class, parentColumns = "id", childColumns = "goodid")})
public class OrderContent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "orderid")
    long orderid;
    @ColumnInfo(name = "count")
    int count;
    @ColumnInfo(name = "goodid")
    int goodid;

    public OrderContent(long orderid, int goodid, int count){
        this.goodid = goodid;
        this.orderid = orderid;
        this.count = count;

    }

    public OrderContent(){

    }

}
