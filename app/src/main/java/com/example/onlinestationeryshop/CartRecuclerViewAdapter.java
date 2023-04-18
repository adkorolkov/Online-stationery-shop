package com.example.onlinestationeryshop;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.CartItemBinding;
import com.example.onlinestationeryshop.databinding.ImageItemBinding;
import com.example.onlinestationeryshop.databinding.ItemGoodsBinding;

import java.util.ArrayList;
import java.util.List;

public class CartRecuclerViewAdapter extends RecyclerView.Adapter<CartRecuclerViewAdapter.RecyclerViewItemViewHolder> {



    public String CHANNEL = "CART_DELETE";
    public  String INFO = "DETELE";
    private Handler h;
    private Server server = Server.getInstance();
    Context ctx;


    LayoutInflater lInflater;
    ArrayList<Good> objects;

    public CartRecuclerViewAdapter(Context context, ArrayList<Good> goods) {
        ctx = context;
        objects = goods;
        for (int i=0;i<objects.size();i++){
            System.out.println(objects.get(i).getId() + "qqq");
        }
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(ArrayList<Good> newData) {
        objects = newData;

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding mBinding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new RecyclerViewItemViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemViewHolder holder, int po) {
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFO, msg.obj.toString()); // добавляем в интент данные
                ctx.sendBroadcast(i); // рассылаем
            }
        };
        // используем созданные, но не используемые view

        Good p = getGood(holder.getAdapterPosition());
        System.out.println(p.getId() + "getPidor");
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        String name = p.getName();
        if(name.length() > 16){
            String h = "";
            for(int i=0;i < 13;i++){
                h+=name.charAt(i);
            }
            name = h + "...";
        }
        else if(name.length()<16){
            int n = 19-name.length();
            for (int i=0;i<n;i++){
                name += " ";
            }
        }
        System.out.println(name.length() + "lenmame");
        String a = Integer.toString(p.getPrice() * server.getItemCount(p.getId()));
        ((TextView) holder.binding.priceTotal).setText(a + " ₽");
        ((TextView) holder.binding.name).setText(name);
        ((TextView) holder.binding.countI).setText(Integer.toString(server.getItemCount(p.getId())));
        ((ImageView) holder.binding.icon).setImageResource(p.getImage_prev());
        ((ImageButton) holder.binding.minusC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.countI.getText().toString().equals("2")) {
                    holder.binding.minusC.setClickable(false);
                    holder.binding.minusC.setBackgroundColor(holder.binding.minusC.getContext().getResources().getColor(R.color.gray));
                    Message msg = new Message();
                    msg.obj = Integer.toString(-1);
                    h.sendMessage(msg);
                    Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) - 1;
                    holder.binding.countI.setText(i.toString());
                    server.changeCartItem(p.getId(), -1);
                    holder.binding.priceTotal.setText(Integer.toString(p.getPrice() * server.getItemCount(p.getId())));
                } else {
                    Message msg = new Message();
                    msg.obj = Integer.toString(-1);
                    h.sendMessage(msg);
                    Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) - 1;
                    holder.binding.countI.setText(i.toString());
                    server.changeCartItem(p.getId(), -1);
                    holder.binding.priceTotal.setText(Integer.toString(p.getPrice() * server.getItemCount(p.getId())));
                }
            }
        });
        if (server.getItemCount(p.getId())==1) {
            holder.binding.minusC.setBackgroundColor(holder.binding.minusC.getContext().getResources().getColor(R.color.gray));
            holder.binding.minusC.setClickable(false);
        }
        ((ImageButton) holder.binding.cansel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(p.getId());
                System.out.println("Delete in adapter " + p.getId());
                h.sendMessage(msg);
            }
        });
        ((ImageButton) holder.binding.plusC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(-1);
                h.sendMessage(msg);
                Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) + 1;
                System.out.println("i== " + i);
                if (i==2){
                    holder.binding.minusC.setClickable(true);
                    holder.binding.minusC.setBackgroundColor(holder.binding.minusC.getContext().getResources().getColor(R.color.prozr));
                }
                holder.binding.countI.setText(i.toString());
                server.changeCartItem(p.getId(), 1);
                holder.binding.priceTotal.setText(Integer.toString(p.getPrice() * server.getItemCount(p.getId())));

            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    Good getGood(int position) {
        return ((Good) getItem(position));
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public CartItemBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CartItemBinding.bind(itemView);
        }
    }
}

