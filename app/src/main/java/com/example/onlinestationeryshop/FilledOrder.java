package com.example.onlinestationeryshop;

import android.util.Pair;

import java.util.ArrayList;

public class FilledOrder extends Order{
    ArrayList<Pair<String, Pair<Integer, Integer>>> goods;

    public int getOrderId() {
        return orderId;
    }

    public String getStatus(){
        return this.status;
    }
    public String getTime(){
        return this.time;
    }

    int orderId;

    public FilledOrder(ArrayList<Pair<String, Pair<Integer, Integer>>> g, int id, String status, String time){
        this.orderId = id;
        this.status = status;
        this.time= time;
        this.goods = g;
    }


    public Integer getPrice(){
        Integer k = 0;
        for (int i=0;i<goods.size();i++){
            k+=goods.get(i).second.first * goods.get(i).second.second;
        }
        return k;
    }

    public String getItems(){
        String k = "";
        for (int i=0;i<goods.size();i++){
            k+=goods.get(i).first + " " + goods.get(i).second.second + " штук" + ";";
        }
        return k;
    }

    public FilledOrder(){

    }
}
