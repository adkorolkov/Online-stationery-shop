package com.example.onlinestationeryshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Server {


    public long getCurrentIdForOrders() {
        return currentIdForOrders;
    }

    public void setCurrentIdForOrders(long currentIdForOrders) {
        this.currentIdForOrders = currentIdForOrders;
    }

    public HashMap<Integer,FilledOrder> orders;
    private long currentIdForOrders;
    private FilledOrder currentOrder;
    public String INFOGOOD = "INFOGOOD";
    public String INFOORDER = "INFOORDER";
    public String INFOADDORDER = "AddOrder";
    public String CHANNEL = "Canal";
    private ArrayList<Good> searchresult;


    private ArrayList<Good> all;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceorder;
    private DatabaseReference databaseReferencegood;

    private static Server instance;
    private String search_n = "";
    private DataBase db;
    private String UserKey = "User";
    private String GoodKey  = "Good";
    private Server(Context contex){
        searchresult = new ArrayList<>();
        orders = new HashMap<>();
        context = contex;
        db = Room.databaseBuilder(contex, DataBase.class, "stationery").allowMainThreadQueries().build();
        GoodDao goodDao = db.goodDao();
    }



    public static synchronized Server getInstance(Context context) {
        if (instance == null) {
            instance = new Server(context);
        }
        return instance;
    }


    public void addToCart(int id, int number, String name, int price){
        CartDao cartDao = db.cartDao();
        if (cartDao.getByGoodId(id)!=null && cartDao.getByGoodId(id).count>0){
            changeCartItem(id, number);
        }
        else {
            cartDao.insert(new Cart(id, number, name, price));
        }
    }





    public void changeCartItem(int id, int number){
        CartDao cartDao = db.cartDao();
        Cart cart1 = cartDao.getByGoodId(id);
        cart1.count += number;
        cartDao.update(cart1);
    }

    public void sendAddOrderMessage(){
        Handler h;
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFOADDORDER, msg.obj.toString()); // добавляем в интент данные
                context.sendBroadcast(i); // рассылаем
            }
        };
        Message msg = new Message();
        msg.obj = "true";
        h.sendMessage(msg);
    }

    public void deleteCartItem(int id){
        CartDao cartDao = db.cartDao();
        cartDao.deleteById(id);
    }

    public ArrayList<History> fillDatas(){
        HistoryDao historyDao = db.historyDao();
        List<History> history = historyDao.getLast(20);
        return fillData(history);
    }

    private ArrayList<History> fillData(List<History> e) {
        ArrayList<History> listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
        return listitem;
    }

    public void insertConfig(String name, String value){
        ConfigDao configDao = db.configDao();
        configDao.insert(new Config(name, value));
    }

    public void updateConfig(String name, String value){
        ConfigDao configDao = db.configDao();
        Config updateble = configDao.getByName(name);
        updateble.value = value;
        configDao.update(updateble);
    }

    public void deleteConfig(String name){
        ConfigDao configDao = db.configDao();
        configDao.delete(name);
    }

    public void addUser(String email, String password){
        deleteAllConfig();
        insertConfig("email", email);
        insertConfig("password", Integer.toString(password.hashCode()));
        insertConfig("enter", "true");
        fillOrders(email);
    }
    public void deleteAllConfig(){
        ConfigDao configDao = db.configDao();
        configDao.deleteAll();
    }

    public void deleteAllHistory(){
        HistoryDao historyDao = db.historyDao();
        historyDao.deleteAll();
    }

    public boolean isItemInCart(Integer id){
        CartDao cartDao = db.cartDao();
        Cart q = cartDao.getByGoodId(id);
        boolean a = q!=null;
        if (a) {
            a = cartDao.getByGoodId(id).count > 0;
        }
        List<Cart> r = cartDao.getAll();
        for (int i=0;i<r.size();i++){
            System.out.println(r.get(i).goodid + "  " + r.get(i).count);
        }
        System.out.println("id  "+id + "isincart  "+a);
        System.out.println(cartDao.getCartCount());
        return a;
    }

    public Integer getItemCount(Integer r){
        CartDao cartDao = db.cartDao();
        return cartDao.getItemCount(r);
    }

    public Integer getCartCount(){
        CartDao cartDao = db.cartDao();
        if(cartDao!=null) {
            return cartDao.getCartCount();
        }
        else{
            return null;
        }
        }

    public Integer getCartPrice(){
        CartDao cartDao = db.cartDao();
        int i = cartDao.getCartPrice();
        return i;
    }

    public ArrayList<Good> getCartItems(){
        ArrayList<Good> ret = new ArrayList<>();
        ArrayList<Good> q = new ArrayList<>();
        CartDao cartDao = db.cartDao();
        List<Cart> w = cartDao.getAll();
        for(int i=0;i<w.size();i++){
            q.add(getForInd(w.get(i).goodid));
        }
        return q;
    }

    public void setSearch(String search_n) {
        this.search_n = search_n;
    }

    public void setNullCart(){
        CartDao cartDao = db.cartDao();
        cartDao.deleteCartAll();
        System.out.println(cartDao.getCartCount()+"cccart");
    }

    public ArrayList<Good> search(){
        return searchresult;
    }


    public String getEmail(){
        ConfigDao configDao = db.configDao();
        Config d = configDao.getByName("email");
        String q = d.value;
        return q;
    }



    public boolean fillOrders(String email){
        String e = "";
        for (int i=0;i<email.length();i++){
            if (email.charAt(i) == '.') {
                e+='_';
            }
            else{
                e+=email.charAt(i);
            }
        }
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(context);
        HashMap<Integer, FilledOrder> neworders = new HashMap<>();
        Ok ok = new Ok(false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(e);
        Handler h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFOORDER, msg.obj.toString()); // добавляем в интент данные
                context.sendBroadcast(i); // рассылаем
            }
        };
        Thread thread;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot Snapshot : snapshot.getChildren()) {
                            try {
                                //String a = Snapshot.getValue().toString(); // parsing from json
                                // "snapshot " + a);
                                FilledOrder fillorder = Snapshot.getValue(FilledOrder.class);
                                neworders.put(fillorder.orderId, fillorder);
                            } catch (Exception e) {
                            }
                        }
                        if(neworders.size()>orders.size()){
                            orders = neworders;
                            ok.setOk(true);
                        }
                        else if((neworders.size()==orders.size()) && neworders.size()>0){

                            for(Map.Entry<Integer, FilledOrder> entry : neworders.entrySet()){
                                if(!entry.getValue().status.equals(orders.get(entry.getKey()).status)){
                                    orders = neworders;
                                    ok.setOk(true);
                                    break;
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                Message msg = new Message();
                msg.obj = "ReadyOr";
                h.sendMessage(msg);
            }
        };
        runnable.run();
        return ok.ok;
    }




    public void findOrders(String email, int id){
        String e = "";
        for (int i=0;i<email.length();i++){
            if (email.charAt(i) == '.') {
                e+='_';
            }
            else{
                e+=email.charAt(i);
            }
        }
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(context);
        final int[] Completed = {0};
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(e);
        Handler h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFOORDER, msg.obj.toString()); // добавляем в интент данные
                context.sendBroadcast(i); // рассылаем
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot Snapshot : snapshot.getChildren()) {
                            try {
                                //String a = Snapshot.getValue().toString(); // parsing from json
                                // "snapshot " + a);
                                FilledOrder fillorder = Snapshot.getValue(FilledOrder.class);

                                if(fillorder.orderId==id){
                                    currentOrder = fillorder;
                                    return;
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                Message msg = new Message();
                msg.obj = "ReadyFind";
                h.sendMessage(msg);
            }
        };
    }



    public void addToOrder(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order("Создан", dtf.format(now));
        OrderDao orderDao = db.orderDao();
        OrderContentDao orderContentDao = db.orderContentDao();
        long orderId = orderDao.insert(order);
        setCurrentIdForOrders(orderId);
        CartDao cartDao = db.cartDao();
        List<Cart> cart = cartDao.getAll();
        ArrayList<OrderContent> resultOrder = new ArrayList<>();
        for (int i=0;i<cart.size();i++){
            OrderContent u = new OrderContent(orderId, cart.get(i).goodid, cart.get(i).count, cart.get(i).goodname, cart.get(i).price);
            resultOrder.add(u);
            orderContentDao.insert(u);
        }
        setOrderToFirebase(order, resultOrder);
    }

    public void searchgoods(){
        ArrayList<Good> ret = new ArrayList<>();
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(context);
        final int[] Completed = {0};
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Good");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot Snapshot : snapshot.getChildren()) {
                            try {
                                Good good = Snapshot.getValue(Good.class);
                                String q = good.getName().toLowerCase();
                                good.setDescription(good.getDescription().replace('_', '\n'));
                                if (q.contains(search_n)) {
                                    ret.add(good);
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                }
                            } catch (Exception e) {
                            }
                        }
                        searchresult = ret;
                        if (search_n.equals("")){
                            all = ret;
                        }
                        Handler h = new Handler(Looper.getMainLooper()){
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                                i.putExtra(INFOGOOD, msg.obj.toString()); // добавляем в интент данные
                                context.sendBroadcast(i); // рассылаем
                            }
                        };
                        Message msg = new Message();
                        msg.obj = "ReadyGo";
                        h.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }


    public void setOrderToFirebase(Order order, ArrayList<OrderContent> orderContents){
        FirebaseApp.initializeApp(context);
        for (int i=0;i<orderContents.size();i++){
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceorder= firebaseDatabase.getReference("Users");
        ConfigDao configDao = db.configDao();
        Config d = configDao.getByName("email");
        String q = d.value;
        String e = "";
        for (int i=0;i<q.length();i++){
            if (q.charAt(i) == '.') {
                e+='_';
            }
            else{
                e+=q.charAt(i);
            }
        }
        ArrayList<OrderGoodListItem> g = new ArrayList<>();
        for (int i=0;i<orderContents.size();i++){
            g.add(new OrderGoodListItem(orderContents.get(i).goodname, orderContents.get(i).price, orderContents.get(i).count));
        }
        FilledOrder filledOrder = new FilledOrder(g,(int)currentIdForOrders, order.status, order.time);
        databaseReferenceorder.child(e).child(Long.toString(currentIdForOrders)).setValue(filledOrder);
    }

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    public String getConfig(String name){
        ConfigDao configDao = db.configDao();
        Config h = configDao.getByName(name);
        if(h!=null){
            return h.value;
        }
        else{
            return null;
        }
    }

    public String getMonth(String i){
        HashMap<String, String> month = new HashMap<>();
        month.put("01", "января");
        month.put("02","февраля");
        month.put("03","марта");
        month.put("04","апреля");
        month.put("05","мая");
        month.put("06","июня");
        month.put("07","июля");
        month.put("08","августа");
        month.put("09","сентября");
        month.put("10","октября");
        month.put("11","ноября");
        month.put("12","декабря");
        return month.get(i);
    }





    public void changeStatusOrder(String status, int id){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ConfigDao configDao = db.configDao();
                Config d = configDao.getByName("email");
                String email = d.value;
                String e = "";
                for (int i=0;i<email.length();i++){
                    if (email.charAt(i) == '.') {
                        e+='_';
                    }
                    else{
                        e+=email.charAt(i);
                    }
                }
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReferenceorder= firebaseDatabase.getReference("Users");
                databaseReferenceorder.child(e).child(Integer.toString(id)).child("status").setValue(status);
                Handler h = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Intent i = new Intent(CHANNEL); // интент для отправки ответа
                        i.putExtra(INFOORDER, msg.obj.toString()); // добавляем в интент данные
                        context.sendBroadcast(i); // рассылаем
                    }
                };
                Message msg = new Message();
                msg.obj = "ChangeStatus";
                h.sendMessage(msg);
            }
        };
        runnable.run();
    }

    public Good getForInd(int i){
        Good res = null;
        for (int j=0;j<all.size();j++){
            Good f = all.get(j);
            if (f.getIdg()==i){
                res = f;
            }
        }
        return res;
    }
}
