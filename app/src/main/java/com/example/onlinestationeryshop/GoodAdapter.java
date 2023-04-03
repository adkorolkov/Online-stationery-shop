package com.example.onlinestationeryshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GoodAdapter extends BaseAdapter {
    Context ctx;
    private OnItemClickListener listener;
    LayoutInflater lInflater;
    ArrayList<Good> objects;

    GoodAdapter(Context context, ArrayList<Good> goods, OnItemClickListener listene) {
        ctx = context;
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listener = listene;
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_goods, parent, false);
        }

        Good p = getGood(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(p.getId());
            }
        });

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        String name = p.getName();
        if(name.length() > 15){
            String h = "";
            for(int i=0;i < 13;i++){
                h+=name.charAt(i);
            }
            name = h + "...";
        }
        ((TextView) view.findViewById(R.id.description)).setText(p.getDescriptionShort() + "");
        ((TextView) view.findViewById(R.id.price)).setText(p.getPrice() + " рублей");
        ((TextView) view.findViewById(R.id.name)).setText(name);
        ((ImageView) view.findViewById(R.id.icon)).setImageResource(p.getImage_prev());
        ((Button) view.findViewById(R.id.add_to_cart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Нажата клавиша добавления в корзину " + (position+1));
            }
        });
        return view;
    }

    // товар по позиции
    Good getGood(int position) {
        return ((Good) getItem(position));
    }

    // содержимое корзины


    // обработчик для чекбоксов
}