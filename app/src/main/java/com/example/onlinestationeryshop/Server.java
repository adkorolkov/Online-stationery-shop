package com.example.onlinestationeryshop;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private static Server instance;
    private ArrayList<Good> goods;
    private HashMap<Integer, Integer> cart;

    private Server(){
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
        for (int i = 0; i < 20; i++) {
            e.add(new Good(R.drawable.mishe, "Суперская игровая мышь, которая позволит нагибать всех ботов", 1500, "Мышь компьютерная", 2*i, "В комплекте поставляется 2 мышки, в красном и белом варианте, чтобы можно было делиться с другом как Польшой. Также много кнопок - целых 3", images_mi));
            e.add(new Good(R.drawable.tractor, "Колесо трактора, лучший транспорт", 19000, "Колесо трактора", 2*i+1, "Колесо трактора - лучший транспорт до вуза, быстрее метро", images_tr));
        }
        goods = e;
        cart = new HashMap<>();
    }


    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }


    public void addToCart(int id, int number){

        cart.put(id, number);
    }

    public void changeCartItem(int id, int number){
        int y = cart.get(id);
        cart.replace(id, y+number);
    }

    public void deleteCartItem(int id){
        cart.remove(id);
    }

    public HashMap<Integer, Integer> getCart(){
        return cart;
    }

    public boolean isItemInCart(Integer id){
        return cart.containsKey(id);
    }

    public Integer getCartCount(){
        int k = 0;
        List<Integer> list = new ArrayList<Integer>(cart.values());
        for (int i=0;i<list.size();i++){
            k+=list.get(i);
        }
        return k;
    }

    public ArrayList<Good> search(String name){
        ArrayList<Good> ret = new ArrayList<>();
        for(int i=0;i< goods.size();i++){
            String goodName = goods.get(i).getName();
            goodName = goodName.toLowerCase();
            if(goodName.contains(name)){
                ret.add(goods.get(i));
            }
        }
        return ret;
    }

    public Good getForInd(int i){
        return goods.get(i);
    }
}
