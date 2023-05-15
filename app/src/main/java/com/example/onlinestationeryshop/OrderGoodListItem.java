package com.example.onlinestationeryshop;

public class OrderGoodListItem {
    private String name;
    private int price;
    private int count;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public OrderGoodListItem(){

    }

    public OrderGoodListItem(String name, int price, int count){
        this.count = count;
        this.name = name;
        this.price = price;
    }
}
