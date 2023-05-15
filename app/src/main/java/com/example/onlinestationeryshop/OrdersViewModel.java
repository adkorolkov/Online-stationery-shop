package com.example.onlinestationeryshop;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersViewModel extends AndroidViewModel {
    public OrdersViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void changeStatusOrder(String status, int id){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.changeStatusOrder(status, id);
    }


    public String getEmail(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.getEmail();
    }
    public List<FilledOrder> getFilledOrders(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        List<FilledOrder> orders = new ArrayList<>();
        HashMap<Integer, FilledOrder> orderlist = server.orders;
        for (Map.Entry<Integer, FilledOrder> entry : orderlist.entrySet()){
            orders.add(entry.getValue());
        }
        return orders;
    }


}