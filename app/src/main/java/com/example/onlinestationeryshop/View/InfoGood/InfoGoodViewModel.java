package com.example.onlinestationeryshop.View.InfoGood;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;

public class InfoGoodViewModel extends AndroidViewModel {
    public InfoGoodViewModel(@NonNull Application application) {
        super(application);
    }


    public boolean isItemInCart(Integer id){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.isItemInCart(id);
    }

    public String getItemCount(int goodId){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.getItemCount(goodId).toString();
    }

    public void deleteCartItem(int goodId){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.deleteCartItem(goodId);
    }

    public void changeCartItem(int goodid, int number){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.changeCartItem(goodid, number);
    }

    public void addToCart(int goodId, int number, String name, int price){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.addToCart(goodId, number, name, price);
    }


}
