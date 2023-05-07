package com.example.onlinestationeryshop;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {


    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
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
        Log.d("qqq", "Initializate firebase");
        ArrayList<Good> g = createGoods();
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("qqq","firebase"+firebaseDatabase);
        databaseReference= firebaseDatabase.getReference(GoodKey);
        Log.d("qqq","Reference"+databaseReference);
        for (int i=0;i<g.size();i++){
            Good good = g.get(i);
            databaseReference.child(Integer.toString(good.getIdg())).setValue(good);
            goodDao.insert(g.get(i));
        }
    }


    private Server(Context contex){
        context = contex;
        db = Room.databaseBuilder(contex, DataBase.class, "stationery").allowMainThreadQueries().build();
        GoodDao goodDao = db.goodDao();
        if (goodDao.getCount()==0){
            fillGoods(goodDao);
        }
    }


    public static synchronized Server getInstance(Context context) {
        if (instance == null) {
            instance = new Server(context);
        }
        return instance;
    }


    public void addToCart(int id, int number){
        CartDao cartDao = db.cartDao();

        System.out.println("CART GETBYID  " + cartDao.getByGoodId(id) + "id  " + id);
        if (cartDao.getByGoodId(id)!=null && cartDao.getByGoodId(id).count>0){
            changeCartItem(id, number);
        }
        else {
            System.out.println("Insert   cartId "  + id);
            cartDao.insert(new Cart(id, number));
        }
    }




    public void changeCartItem(int id, int number){
        CartDao cartDao = db.cartDao();
        Cart cart1 = cartDao.getByGoodId(id);
        if (cart1==null){
            addToCart(id, number);
        }
        else {
            cart1.count += number;
            cartDao.update(cart1);
        }
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
        GoodDao goodDao = db.goodDao();
        ArrayList<Good> ret = new ArrayList<>();
        if (search_n.equals("")){
            List<Good> q =  goodDao.getAll();
            for (int i=0;i<q.size();i++){
                ret.add(q.get(i));
            }
        }
        else{
            List<Good> q = goodDao.getByName(search_n);
            for (int i=0;i<q.size();i++){
                ret.add(q.get(i));
            }
        }
        return ret;
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
        OrderDao orderDao = db.orderDao();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        Order order = orderDao.getByID(id);
        order.status = status;
        orderDao.update(order);
    }

    public Good getForInd(int i){

        return db.goodDao().getByID(i);
    }
}
