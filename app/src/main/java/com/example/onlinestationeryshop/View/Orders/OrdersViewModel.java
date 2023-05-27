package com.example.onlinestationeryshop.View.Orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.models.FilledOrder;
import com.example.onlinestationeryshop.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersViewModel extends AndroidViewModel {
    public OrdersViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void changeStatusOrder(String status, int id){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.changeStatusOrder(status, id);
    }


    public String getEmail(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.getEmail();
    }
    public List<FilledOrder> getFilledOrders(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        List<FilledOrder> orders = new ArrayList<>();
        HashMap<Integer, FilledOrder> orderlist = repository.orders;
        for (Map.Entry<Integer, FilledOrder> entry : orderlist.entrySet()){
            orders.add(entry.getValue());
        }
        Collections.reverse(orders);
        return orders;
    }


    public boolean checkFilledOrders(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.fillOrders(getEmail());
    }

    public String getChannel(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.CHANNEL;
    }

    public String getOrderInfo(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOORDER;
    }

    public String getAddOrderInfo(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOADDORDER;
    }
}