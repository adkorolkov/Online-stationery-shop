package com.example.onlinestationeryshop.View.HistorySearch;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.View.HistorySearch.OnSeacrhItemClickListener;
import com.example.onlinestationeryshop.databinding.SeacrhHistoryItemBinding;
import com.example.onlinestationeryshop.models.History;

import java.util.ArrayList;

public class SeacrhRecyclerAdapter extends RecyclerView.Adapter<SeacrhRecyclerAdapter.RecyclerViewItemViewHolder> {



    public String CHANNEL = "SEARCH_THIS";
    public  String INFO = "SEARCH_THIS_ITEM";
    private Handler h;
    Context ctx;

    private OnSeacrhItemClickListener listener;

    LayoutInflater lInflater;
    ArrayList<History> objects;

    public SeacrhRecyclerAdapter(Context context, ArrayList<History> goods, OnSeacrhItemClickListener listene) {
        ctx = context;
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listener = listene;
    }

    public void updateData(ArrayList<History> newData) {
        objects = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeacrhHistoryItemBinding mBinding = SeacrhHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()));

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

        History p = getSeacrh(holder.getAdapterPosition());
        String value = p.getQuer();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(value);
            }
        });

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка

        ((TextView) holder.binding.search).setText(value+"");
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    History getSeacrh(int position) {
        return ((History) getItem(position));
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public SeacrhHistoryItemBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SeacrhHistoryItemBinding.bind(itemView);
        }
    }
}
