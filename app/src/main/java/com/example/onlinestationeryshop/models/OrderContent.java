package com.example.onlinestationeryshop.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ordercontent",foreignKeys = {@ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderid"),
})
public class OrderContent {
    public int getId() {
        return id;
    }

    public long getOrderid() {
        return orderid;
    }

    public int getCount() {
        return count;
    }

    public int getGoodid() {
        return goodid;
    }

    public int getPrice() {
        return price;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "orderid")
    long orderid;
    @ColumnInfo(name = "count")
    int count;
    @ColumnInfo(name = "goodid")
    int goodid;

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    @ColumnInfo(name = "goodname")
    String goodname;

    @ColumnInfo(name = "price")
    int price;

    public OrderContent(long orderid, int goodid, int count, String goodname, int price){
        this.goodid = goodid;
        this.orderid = orderid;
        this.count = count;
        this.goodname = goodname;
        this.price = price;

    }

    public OrderContent(){

    }

}
