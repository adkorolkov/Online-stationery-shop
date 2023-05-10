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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now)+"fffff");
        Order order = new Order("Создан", dtf.format(now));
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        OrderDao orderDao = db.orderDao();
        OrderContentDao orderContentDao = db.orderContentDao();
        long orderId = orderDao.insert(order);
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.setCurrentIdForOrders(orderId);
        CartDao cartDao = db.cartDao();
        List<Cart> cart = cartDao.getAll();
        ArrayList<OrderContent> resultOrder = new ArrayList<>();
        for (int i=0;i<cart.size();i++){
            OrderContent u = new OrderContent(orderId, cart.get(i).goodid, cart.get(i).count, cart.get(i).goodname, cart.get(i).price);
            resultOrder.add(u);
            orderContentDao.insert(u);
        }
        server.setOrderToFirebase(order, resultOrder);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Order order1 = orderDao.getByID((int) orderId);
                if (!order1.status.equals("Отменён")) {
                    order1.status = "Готов";
                    orderDao.update(order1);
                    h = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            Intent i = new Intent(CHANNEL); // интент для отправки ответа
                            i.putExtra(INFO, msg.obj.toString()); // добавляем в интент данные
                            ctx.sendBroadcast(i); // рассылаем
                        }
                    };
                }
                Message msg = new Message();
                msg.obj = "change";
                h.sendMessage(msg);
            }
        }, 20000);

    }

    public void sendMessage(){
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFO, msg.obj.toString()); // добавляем в интент данные
                ctx.sendBroadcast(i); // рассылаем
            }
        };
        Message msg = new Message();
        msg.obj = "true";
        h.sendMessage(msg);
    }
}
