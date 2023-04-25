package com.example.onlinestationeryshop;

import android.util.Pair;

import java.util.ArrayList;

public class FilledOrder extends Order{
    ArrayList<Pair<Good, Integer>> goods;

    public FilledOrder(ArrayList<Pair<Good, Integer>> g,String status, String time, int id){
        this.id = id;
        this.status = status;
        this.time= time;
        this.goods = g;
    }

    public Integer getPrice(){
        Integer k = 0;
        for (int i=0;i<goods.size();i++){
            k+=goods.get(i).first.getPrice()* goods.get(i).second;
        }
        return k;
    }

    public String getItems(){
        String k = "";
        for (int i=0;i<goods.size();i++){
            k+=goods.get(i).first.getName() + " " + goods.get(i).second + " штук" + "\n";
        }
        return k;
    }
}
