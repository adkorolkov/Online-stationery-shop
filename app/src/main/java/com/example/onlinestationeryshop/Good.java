package com.example.onlinestationeryshop;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Good {

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    private int image_prev;

    public int getImage_prev() {
        return image_prev;
    }

    public ArrayList<Integer>  getImages() {
        return images;
    }

    private ArrayList<Integer> images;

    public String getDescriptionShort() {
        return descriptionShort;
    }

    private String descriptionShort;
    private String description;
    private Integer price;
    private String name;

    public int getId() {
        return id;
    }

    private int id;


    public Good(int imag,  String desshort, Integer pr, String nam, int i,String des, ArrayList<Integer> r){
        image_prev = imag;
        description = des;
        price = pr;
        name = nam;
        id = i;
        descriptionShort = desshort;
        images = r;
    }
}
