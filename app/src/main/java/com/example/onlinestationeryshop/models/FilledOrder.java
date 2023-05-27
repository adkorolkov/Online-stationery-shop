package com.example.onlinestationeryshop.models;

import java.util.ArrayList;

public class FilledOrder extends Order {
    public ArrayList<OrderGoodListItem> getGoods() {
        return goods;
    }

    ArrayList<OrderGoodListItem> goods;

    public int getOrderId() {
        return orderId;
    }



    int orderId;

    public FilledOrder(ArrayList<OrderGoodListItem> g, int id, String status, String time){
        this.orderId = id;
        this.setStatus(status);
        this.setTime(time);
        this.goods = g;
    }


    public Integer getPrice(){
        Integer k = 0;
        for (int i=0;i<goods.size();i++){
            k+=goods.get(i).getPrice() * goods.get(i).getCount();
        }
        return k;
    }

    //public String getItems(){
    //    String k = "";
    //    for (int i=0;i<goods.size();i++){
    //        k+=goods.get(i).first + " " + goods.get(i).second.second + " штук" + "\n";
    //    }
     //   return k;
    //}

    public FilledOrder(){

    }
}
