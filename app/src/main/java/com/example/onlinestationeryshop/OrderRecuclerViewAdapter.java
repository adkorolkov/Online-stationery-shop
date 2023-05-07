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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.OrderlistItemBinding;


import java.util.ArrayList;
import java.util.List;

public class OrderRecuclerViewAdapter extends RecyclerView.Adapter<OrderRecuclerViewAdapter.RecyclerViewItemViewHolder> {



    public String CHANNEL = "OrderChangeStatus";
    public  String INFO = "Change";
    private Handler h;

    Context ctx;
    LayoutInflater lInflater;
    List<FilledOrder> objects;

    public OrderRecuclerViewAdapter(Context context, List<FilledOrder> goods) {
        ctx = context;
        objects = goods;
        for (int i=0;i< objects.size();i++){
            System.out.println(objects.get(i).getItems());
        }
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
        System.out.println("id of FilledOrder  " + Integer.toString(p.id));
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка

        ((TextView) holder.binding.orderid).setText("Заказ №" + Integer.toString(p.id));
        ((TextView) holder.binding.price).setText(p.getPrice().toString() + " ₽");
        ((TextView) holder.binding.status).setText(p.status);
        String q = p.time;
        String res = "";
        Server server = Server.getInstance(ctx);
        if (!q.substring(0,4).equals(server.getTime().substring(0,4))){
            res+=q.substring(0,4) + " ";
        }
        if (q.substring(8,9).equals("0")) {
            res += q.substring(9, 10) + " ";
        }
        else{
            res += q.substring(8, 10) + " ";
        }
        String e = q.substring(5,7);
        res+=server.getMonth(q.substring(5,7)) + " ";
        res+=q.substring(11,16);
        ((TextView) holder.binding.time).setText(res);
        if (p.status.equals("Отменён") || p.status.equals("Получен")){
            holder.binding.canselOrder.setVisibility(View.GONE);
            holder.binding.getOrder.setVisibility(View.GONE);
        }
        else if (p.status.equals("Создан")){
            holder.binding.getOrder.setVisibility(View.GONE);
            holder.binding.canselOrder.setVisibility(View.VISIBLE);
        }
        else if (p.status.equals("Готов")){
            holder.binding.canselOrder.setVisibility(View.GONE);
            holder.binding.getOrder.setVisibility(View.VISIBLE);
        }
        ((Button) holder.binding.canselOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(p.id)+" Cansel";
                h.sendMessage(msg);
            }
        });
        holder.binding.getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = Integer.toString(p.id)+" Ready";
                h.sendMessage(msg);
            }
        });
        ((TextView) holder.binding.goodlist).setText(p.getItems());
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

