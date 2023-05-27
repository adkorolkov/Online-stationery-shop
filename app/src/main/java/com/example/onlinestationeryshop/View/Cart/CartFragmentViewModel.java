package com.example.onlinestationeryshop.View.Cart;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;
import com.example.onlinestationeryshop.models.Good;

import java.util.ArrayList;

public class CartFragmentViewModel  extends AndroidViewModel {
    public CartFragmentViewModel(@NonNull Application application) {

        super(application);
        ctx = getApplication().getApplicationContext();
    }

    public String CHANNEL = "OrderAdd";
    public  String INFO = "AddOrder";

    Handler h;
    Context ctx;


    public void addToOrder(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.addToOrder();
    }


    public ArrayList<Good> getCartItems(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.getCartItems();
    }

    public Integer getCartCount(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        Integer r = repository.getCartCount();
        if(r!=null){
            return r;
        }
        else{
            return 0;
        }
    }


    public void deleteCartItem(int id){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.deleteCartItem(id);
    }
    public void setNullCart() {
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.setNullCart();
    }


    public Integer getCartPrice(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.getCartPrice();
    }

    public void sendMessage(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.sendAddOrderMessage();
    }
}
