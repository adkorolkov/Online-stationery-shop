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
    public String CHANNEL = "Canal";
    private ArrayList<Good> searchresult;
    private ArrayList<Good> all;
    private ArrayList<Good> cart;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceorder;
    private DatabaseReference databaseReferencegood;

    private static Server instance;
    private String search_n = "";
    private DataBase db;
    private String UserKey = "User";
    private String GoodKey  = "Good";


    private ArrayList<Good> createGoods(){
        ArrayList<Good> e = new ArrayList<>();
        ArrayList<Integer> images_tr = new ArrayList<>();
        images_tr.add(R.drawable.tractor);
        images_tr.add(R.drawable.orange_will);
        images_tr.add(R.drawable.red_will);
        images_tr.add(R.drawable.will_gorizont);
        ArrayList<Integer> images_mi = new ArrayList<>();
        images_mi.add(R.drawable.mishe);
        images_mi.add(R.drawable.orange_mi);
        images_mi.add(R.drawable.heart_mi);
        images_mi.add(R.drawable.circle_mi);
        ArrayList<Integer> images_me = new ArrayList<>();
        images_me.add(R.drawable.meme_start);
        images_me.add(R.drawable.meme1);
        images_me.add(R.drawable.meme2);
        images_me.add(R.drawable.meme3);
        int[] a = {1,2,3};
        for (int i = 0; i < 50; i++) {
            java.util.Random random = new java.util.Random();
            int random_computer_card = random.nextInt(a.length);
            if (random_computer_card==0){
                e.add(new Good(R.drawable.mishe, "Суперская игровая мышь, которая позволит нагибать всех ботов", 1500, "Мышь компьютерная", "В комплекте поставляется 2 мышки, в красном и белом варианте, чтобы можно было делиться с другом как Польшой. Также много кнопок - целых 3", images_mi,i));
            }
            else if (random_computer_card==1){
                e.add(new Good(R.drawable.tractor, "Колесо трактора, лучший транспорт", 90000, "Колесо трактора", "Колесо трактора - лучший транспорт до вуза, быстрее метро", images_tr,i));
            }
            else if (random_computer_card==2){
                e.add(new Good(R.drawable.meme_start, "Мемы жёсткие для жёсткого вкида на паре", 200, "Мемы жёсткие", "Если вам хочется fuck, то нужно срочно покупать это для жёсткого вкида. Всегда ждём снова", images_me,i));
            }
        }
        return e;
    }


    private void fillGoods(GoodDao goodDao){
        ArrayList<Good> g = createGoods();
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferencegood= firebaseDatabase.getReference(GoodKey);
        for (int i=0;i<g.size();i++){
            Good good = g.get(i);
            databaseReferencegood.child(Integer.toString(good.getIdg())).setValue(good);
            goodDao.insert(g.get(i));
        }
    }


    private Server(Context contex){
        searchresult = new ArrayList<>();
        orders = new HashMap<>();
        cart = new ArrayList<>();
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

    public void deleteCartItem(int id){
        CartDao cartDao = db.cartDao();
        cartDao.deleteById(id);
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
        return cartDao.getCartCount();
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

    public void fillOrders(String email){
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
                                orders.put(fillorder.orderId, fillorder);
                            } catch (Exception e) {
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
        runnable.run();
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
        runnable.run();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
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
