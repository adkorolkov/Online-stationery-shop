package com.example.onlinestationeryshop;

import java.util.ArrayList;

public class Server {
    private ArrayList<Good> goods;

    public Server(ArrayList<Good> e){
        goods = e;
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
