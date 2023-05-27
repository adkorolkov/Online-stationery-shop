package com.example.onlinestationeryshop.View.Catalog;

import android.content.Context;
import android.widget.ImageView;

import com.example.onlinestationeryshop.Repository;
import com.squareup.picasso.Picasso;

public class GoodRecyclerViewModelView {

    public void setResource(Context context, String url, ImageView image){
        Picasso.with(context).load(url).into(image);
    }

    public boolean isItemInCart(Context context, int goodId){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        return repository.isItemInCart(goodId);
    }

    public void changeCartItem(Context context, int goodId, int number){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        repository.changeCartItem(goodId, number);
    }

    public void addCartItem(Context context,int goodId, int number, String name, Integer price){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        repository.addToCart(goodId, number, name, price);
    }

    public GoodRecyclerViewModelView(){

    }
}
