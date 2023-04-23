package com.example.onlinestationeryshop;

import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Server {

    private static Server instance;
    private String search_n = "";
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
                e.add(new Good(R.drawable.mishe, "Суперская игровая мышь, которая позволит нагибать всех ботов", 1500, "Мышь компьютерная", i, "В комплекте поставляется 2 мышки, в красном и белом варианте, чтобы можно было делиться с другом как Польшой. Также много кнопок - целых 3", images_mi));
            }
            else if (random_computer_card==1){
                e.add(new Good(R.drawable.tractor, "Колесо трактора, лучший транспорт", 90000, "Колесо трактора", i, "Колесо трактора - лучший транспорт до вуза, быстрее метро", images_tr));
            }
            else if (random_computer_card==2){
                e.add(new Good(R.drawable.meme_start, "Мемы жёсткие для жёсткого вкида на паре", 200, "Мемы жёсткие", i, "Если вам хочется fuck, то нужно срочно покупать это для жёсткого вкида. Всегда ждём снова", images_me));
            }
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

    public Integer getItemCount(Integer r){
        return cart.get(r);
    }

    public Integer getCartCount(){
        int k = 0;
        List<Integer> list = new ArrayList<Integer>(cart.values());
        for (int i=0;i<list.size();i++){
            k+=list.get(i);
        }
        return k;
    }

    public Integer getCartPrice(){
        int q = 0;
        Set<Integer> setKeys = cart.keySet();
        for(Integer k: setKeys){
            q+=(getForInd(k).getPrice()*cart.get(k));
        }
        return q;
    }

    public ArrayList<Good> getCartItems(){
        ArrayList<Good> q = new ArrayList<>();
        Set<Integer> setKeys = cart.keySet();
        for(Integer k: setKeys){
            q.add(getForInd(k));
        }
        return q;
    }

    public void setSearch(String search_n) {
        this.search_n = search_n;
    }

    public void setNullCart(){
        cart = new HashMap<>();
    }

    public ArrayList<Good> search(){
        ArrayList<Good> ret = new ArrayList<>();
        for(int i=0;i< goods.size();i++){
            String goodName = goods.get(i).getName();
            goodName = goodName.toLowerCase();
            if(goodName.contains(search_n)){
                ret.add(goods.get(i));
            }
        }
        return ret;
    }

    public Good getForInd(int i){
        return goods.get(i);
    }
}
