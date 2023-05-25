package com.example.onlinestationeryshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.ImageItemBinding;
import com.example.onlinestationeryshop.databinding.ItemGoodsBinding;

import java.util.ArrayList;
import java.util.List;

public class GoodRycycleAdapter extends RecyclerView.Adapter<GoodRycycleAdapter.RecyclerViewItemViewHolder> {



    public String CHANNEL = "CART_ADD";
    public  String INFO = "ADD_TO_CART";
    private Handler h;
    Context ctx;

    private OnItemClickListener listener;

    LayoutInflater lInflater;
    ArrayList<Good> objects;

    public GoodRycycleAdapter(Context context, ArrayList<Good> goods, OnItemClickListener listene) {
        ctx = context;
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listener = listene;
    }

    public void updateData(ArrayList<Good> newData) {
        objects = newData;

        notifyDataSetChanged();
    }

    private void setClicable(boolean enable, GoodRycycleAdapter.RecyclerViewItemViewHolder holder){
        holder.binding.addToCart.setClickable(enable);
    }

    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGoodsBinding mBinding = ItemGoodsBinding.inflate(LayoutInflater.from(parent.getContext()));

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(p.getIdg());
            }
        });

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        String name = p.getName();
        if(name.length() > 18) {
            String h = "";
            for (int i = 0; i < 16; i++) {
                h += name.charAt(i);
            }
            name = h + "...";
        }
        ((TextView) holder.binding.description).setText(p.getDescriptionShort() + "");
        ((TextView) holder.binding.price).setText(p.getPrice() + " ₽");
        ((TextView) holder.binding.name).setText(name);
        ((ImageView) holder.binding.icon).setImageResource(p.getImage_prev());
        ((Button) holder.binding.addToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicable(false, holder);
                Server server = Server.getInstance(ctx.getApplicationContext());
                if(server.isItemInCart(p.getIdg())){
                    server.changeCartItem(p.getIdg(), 1);
                }
                else {
                    server.addToCart(p.getIdg(), 1, p.getName(), p.getPrice());
                }
                Message msg = new Message();
                msg.obj = Integer.toString(p.getIdg());
                h.sendMessage(msg);
                setClicable(true, holder);
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
        public ItemGoodsBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemGoodsBinding.bind(itemView);
        }
    }
}
