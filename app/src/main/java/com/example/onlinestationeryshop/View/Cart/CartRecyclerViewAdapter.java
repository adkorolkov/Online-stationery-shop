package com.example.onlinestationeryshop.View.Cart;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.CartItemBinding;
import com.example.onlinestationeryshop.models.Good;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.RecyclerViewItemViewHolder> {



    private CartRecyclerAdapterViewModel mViewModel;

    public String CHANNEL = "CART_DELETE";
    public  String INFO = "DETELE";
    private Handler h;
    Context ctx;


    LayoutInflater lInflater;
    ArrayList<Good> objects;

    public CartRecyclerViewAdapter(Context context, ArrayList<Good> goods) {
        ctx = context;
        mViewModel = new CartRecyclerAdapterViewModel(context);
        objects = goods;
        for (int i=0;i<objects.size();i++){
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

    private void setClicable(boolean enable, RecyclerViewItemViewHolder holder){
        holder.binding.minusC.setClickable(enable);
        holder.binding.plusC.setClickable(enable);
        holder.binding.cansel.setClickable(enable);
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
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        String name = p.getName();
        if(name.length() > 16){
            String h = "";
            if(name.length()>=16) {
                for (int i = 0; i < 16; i++) {
                    h += name.charAt(i);
                }
                name = h + "...";
            }
            else {
                h = name + " ";
            }
        }
        else if(name.length()<=16){
            int n = 16-name.length();
            for (int i=0;i<n+1;i++){
                name += " ";
            }
        }
        String a = Integer.toString(p.getPrice() * mViewModel.getItemCount(p.getIdg()));
        ((TextView) holder.binding.priceTotal).setText(a + " ₽");
        ((TextView) holder.binding.name).setText(name);
        ((TextView) holder.binding.countI).setText(Integer.toString(mViewModel.getItemCount(p.getIdg())));
        mViewModel.setResource(ctx, p.getImage_prev(), holder.binding.icon);
        ((ImageButton) holder.binding.minusC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicable(false, holder);
                if (holder.binding.countI.getText().toString().equals("2")) {
                    holder.binding.minusC.setClickable(false);
                    holder.binding.minusC.setVisibility(View.GONE);
                    Message msg = new Message();
                    msg.obj = Integer.toString(-1);
                    h.sendMessage(msg);
                    Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) - 1;
                    holder.binding.countI.setText(i.toString());
                    mViewModel.changeCartItem(p.getIdg(), -1);
                    Integer priceTotal = p.getPrice() * Integer.parseInt(holder.binding.countI.getText().toString());
                    holder.binding.priceTotal.setText(priceTotal.toString() + " ₽");
                } else {
                    Message msg = new Message();
                    msg.obj = Integer.toString(-1);
                    h.sendMessage(msg);
                    Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) - 1;
                    holder.binding.countI.setText(i.toString());
                    mViewModel.changeCartItem(p.getIdg(), -1);
                    Integer priceTotal = p.getPrice() * Integer.parseInt(holder.binding.countI.getText().toString());
                    holder.binding.priceTotal.setText(priceTotal.toString() + " ₽");
                }
                setClicable(true, holder);
            }
        });
        if (mViewModel.getItemCount(p.getIdg())==1) {
            holder.binding.minusC.setVisibility(View.GONE);
            holder.binding.minusC.setClickable(false);
        }
        ((ImageButton) holder.binding.cansel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicable(false, holder);
                Message msg = new Message();
                msg.obj = Integer.toString(p.getIdg());
                System.out.println("Delete in adapter " + p.getIdg());
                h.sendMessage(msg);
                setClicable(true, holder);
            }
        });
        ((ImageButton) holder.binding.plusC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicable(false, holder);
                Message msg = new Message();
                msg.obj = Integer.toString(-1);
                h.sendMessage(msg);
                Integer i = Integer.parseInt(holder.binding.countI.getText().toString()) + 1;
                System.out.println("i== " + i);
                if (i==2){
                    holder.binding.minusC.setClickable(true);
                    holder.binding.minusC.setVisibility(View.VISIBLE);
                }
                holder.binding.countI.setText(i.toString());
                mViewModel.changeCartItem(p.getIdg(), 1);
                Integer priceTotal = p.getPrice() * Integer.parseInt(holder.binding.countI.getText().toString());
                holder.binding.priceTotal.setText(priceTotal.toString() + " ₽");
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
        public CartItemBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CartItemBinding.bind(itemView);
        }
    }
}

