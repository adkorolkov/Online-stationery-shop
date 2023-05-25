package com.example.onlinestationeryshop;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

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
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.addToOrder();
    }


    public ArrayList<Good> getCartItems(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.getCartItems();
    }

    public Integer getCartCount(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        Integer r = server.getCartCount();
        if(r!=null){
            return r;
        }
        else{
            return 0;
        }
    }


    public void deleteCartItem(int id){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.deleteCartItem(id);
    }
    public void setNullCart() {
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.setNullCart();
    }


    public Integer getCartPrice(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.getCartPrice();
    }

    public void sendMessage(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.sendAddOrderMessage();
    }
}
