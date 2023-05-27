package com.example.onlinestationeryshop.View.Cart;

import android.content.Context;
import android.widget.ImageView;

import com.example.onlinestationeryshop.Repository;
import com.squareup.picasso.Picasso;

public class CartRecyclerAdapterViewModel {
    private Context context;

    public CartRecyclerAdapterViewModel(Context context){
        this.context = context;
    }


    public Integer getItemCount(int goodId){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        return repository.getItemCount(goodId);
    }

    public void setResource(Context context, String url, ImageView image){
        Picasso.with(context).load(url).into(image);
    }

    public void changeCartItem(int goodId, int number){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        repository.changeCartItem(goodId, number);
    }
}
