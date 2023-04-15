package com.example.onlinestationeryshop;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.ImageItemBinding;
import com.example.onlinestationeryshop.databinding.ItemGoodsBinding;

import java.util.ArrayList;

public class InfoGoodRecucleAdapter extends RecyclerView.Adapter<InfoGoodRecucleAdapter.RecyclerViewItemViewHolder> {



    Context ctx;


    LayoutInflater lInflater;
    ArrayList<Integer> objects;
    public InfoGoodRecucleAdapter(Context context, ArrayList<Integer> goods) {
        ctx = context;
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(ArrayList<Integer> newData) {
        objects = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding mBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new RecyclerViewItemViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemViewHolder holder, int po) {

        // используем созданные, но не используемые view

        Integer p = getImage(holder.getAdapterPosition());;
        System.out.println(p +"  ImageResourse");

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((ImageView) holder.binding.ima).setImageResource(p);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public Object getItem(int position) {
        return objects.get(position);
    }
    Integer getImage(int position) {
        return ((Integer) getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public ImageItemBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ImageItemBinding.bind(itemView);
        }
    }
}

