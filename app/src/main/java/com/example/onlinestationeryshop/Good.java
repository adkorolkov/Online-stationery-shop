package com.example.onlinestationeryshop;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "good")
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

    public int getImage_prev() {
        return image_prev;
    }

    public void setImage_prev(int image_prev) {
        this.image_prev = image_prev;
    }

    public void setImagelist(String imagelist) {
        this.imagelist = imagelist;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "image_prev")
    private int image_prev;


    public ArrayList<Integer>  getImages() {
        ArrayList<Integer> q = new ArrayList<>();
        String[] w = imagelist.split(",");
        for (int i=0;i<w.length;i++){
            q.add(Integer.parseInt(w[i]));
        }
        return q;
    }

    public String getImagelist() {
        return imagelist;
    }

    @ColumnInfo(name = "imagelist")
    private String imagelist;

    public String getDescriptionShort() {
        return descriptionShort;
    }

    @ColumnInfo(name = "descriptionShort")
    private String descriptionShort;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private int price;
    private String name;

    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    public Good(int imag,  String desshort, Integer pr, String nam,String des, ArrayList<Integer> r){
        image_prev = imag;
        description = des;
        price = pr;
        name = nam;
        descriptionShort = desshort;
        String t = "";
        for (int w=0;w<r.size()-1;w++){
            t+=r.get(w).toString()+",";
        }
        t+=r.get(r.size()-1);
        imagelist = t;
    }

    public Good(){

    }
}
