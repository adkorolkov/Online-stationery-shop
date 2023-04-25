package com.example.onlinestationeryshop;

import android.app.Application;
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
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        OrderDao orderDao = db.orderDao();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        Order order = orderDao.getByID(id);
        order.status = status;
        orderDao.update(order);
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
            List<OrderContent> contents = orderContentDao.getByOrderID(id);
            ArrayList<Pair<Good, Integer>> list = new ArrayList<>();
            int sum = 0;
            for(int j=0;j<contents.size();j++){
                Good q = goodDao.getByID(contents.get(j).goodid);
                list.add(new Pair<>(q, contents.get(j).count));
            }
            FilledOrder filledOrder = new FilledOrder(list, orders.get(i).status, orders.get(i).time, orders.get(i).id);
            returned.add(filledOrder);
        }
        System.out.println("len returned  " + returned.size());
        return returned;
    }


}