package com.example.onlinestationeryshop.View.Orders;

import android.content.Context;

import com.example.onlinestationeryshop.models.OrderGoodListItem;
import com.example.onlinestationeryshop.Repository;

import java.util.ArrayList;

public class OrderRecyclerAdapterViewModel {
    private Context context;

    public OrderRecyclerAdapterViewModel(Context ctx){
        context  =ctx;
    }

    public String getTime(String q){
        Repository repository = Repository.getInstance(context.getApplicationContext());
        String h =  repository.getTime();
        String res = "";
        if (!q.substring(0,4).equals((h.substring(0,4)))){
            res+=q.substring(0,4) + " ";
        }
        if (q.substring(8,9).equals("0")) {
            res += q.substring(9, 10) + " ";
        }
        else{
            res += q.substring(8, 10) + " ";
        }
        res+=repository.getMonth(q.substring(5,7)) + " ";
        res+=q.substring(11,16);
        return res;
    }

    public String getGoodlist(ArrayList<OrderGoodListItem> list){
        String goodlist = "";
        for (int i=0;i<list.size();i++){
            goodlist+=list.get(i).getName() + " в количестве " + list.get(i).getCount() + "\n";
        }
        return goodlist;
    }
}
