package com.example.onlinestationeryshop.View.Orders;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.models.FilledOrder;
import com.example.onlinestationeryshop.databinding.OrderlistItemBinding;


import java.util.List;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.RecyclerViewItemViewHolder> {



    private OrderRecyclerAdapterViewModel mViewModel;
    public String CHANNEL = "OrderChangeStatus";
    public  String INFO = "Change";
    private Handler h;

    Context ctx;
    LayoutInflater lInflater;
    List<FilledOrder> objects;

    public OrderRecyclerViewAdapter(Context context, List<FilledOrder> goods) {
        ctx = context;
        mViewModel = new OrderRecyclerAdapterViewModel(context);
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(List<FilledOrder> newData) {
        objects = newData;

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderlistItemBinding mBinding = OrderlistItemBinding.inflate(LayoutInflater.from(parent.getContext()));

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

        FilledOrder p = getFilledOrder(holder.getAdapterPosition());
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка

        ((TextView) holder.binding.orderid).setText("Заказ №" + Integer.toString(p.getOrderId()));
        ((TextView) holder.binding.price).setText(p.getPrice().toString() + " ₽");
        ((TextView) holder.binding.status).setText(p.getStatus());
        String q = p.getTime();
        String res = mViewModel.getTime(q);
        ((TextView) holder.binding.time).setText(res);
        if (p.getStatus().equals("Отменён") || p.getStatus().equals("Получен")){
            holder.binding.canselOrder.setVisibility(View.GONE);
            holder.binding.getOrder.setVisibility(View.GONE);
        }
        else if (p.getStatus().equals("Создан")){
            holder.binding.getOrder.setVisibility(View.GONE);
            holder.binding.canselOrder.setVisibility(View.VISIBLE);
        }
        else if (p.getStatus().equals("Готов")){
            holder.binding.canselOrder.setVisibility(View.GONE);
            holder.binding.getOrder.setVisibility(View.VISIBLE);
        }
        ((Button) holder.binding.canselOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(p.getOrderId())+" Cansel";
                h.sendMessage(msg);
            }
        });
        holder.binding.getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(p.getOrderId())+" ReadyGet";
                h.sendMessage(msg);
            }
        });
        ((TextView) holder.binding.goodlist).setText(mViewModel.getGoodlist(p.getGoods()));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    FilledOrder getFilledOrder(int position) {
        return ((FilledOrder) getItem(position));
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public OrderlistItemBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = OrderlistItemBinding.bind(itemView);
        }
    }
}

