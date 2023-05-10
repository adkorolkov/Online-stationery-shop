package com.example.onlinestationeryshop;

import android.util.Pair;

import java.util.ArrayList;

public class FilledOrder extends Order{
    ArrayList<Pair<String, Pair<Integer, Integer>>> goods;
    int price;

    public FilledOrder(ArrayList<Pair<String, Pair<Integer, Integer>>> g,String status, String time, int id){
        this.id = id;
        this.status = status;
        this.time= time;
        this.goods = g;
        this.price = price;
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
            k+=goods.get(i).first + " " + goods.get(i).second.second + " штук" + "\n";
        }
        return k;
    }
}
