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
import java.util.List;

public class OrdersViewModel extends AndroidViewModel {
    public OrdersViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void changeStatusOrder(String status, int id){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.changeStatusOrder(status, id);
    }

    public List<FilledOrder> getFilledOrders(){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        OrderDao orderDao = db.orderDao();
        GoodDao goodDao = db.goodDao();
        OrderContentDao orderContentDao = db.orderContentDao();
        List<Order> orders = orderDao.getAll();
        System.out.println("len orders  " + orders.size());
        List<FilledOrder> returned = new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            int id = orders.get(i).id;
            Log.d("qqq", "OrderId in OrderViewModel  "+id);
            List<OrderContent> contents = orderContentDao.getByOrderID(id);
            Log.d("qqq", "contentssize  "+contents.size());
            for (int k = 0;k<contents.size();k++){
                Log.d("qqq", " OrderViewModel "+contents.get(i).getGoodname());
            }
            ArrayList<Pair<String, Pair<Integer, Integer>>> list = new ArrayList<>();
            int sum = 0;
            for(int j=0;j<contents.size();j++){
                OrderContent orderContenti = contents.get(i);
                list.add(new Pair<>(orderContenti.goodname, new Pair<>(orderContenti.price, orderContenti.count)));
            }
            Server server = Server.getInstance(getApplication().getApplicationContext());
            FilledOrder filledOrder = new FilledOrder(list, orders.get(i).id,orders.get(i).status, orders.get(i).time);
            returned.add(filledOrder);
        }
        System.out.println("len returned  " + returned.size());
        return returned;
    }


}