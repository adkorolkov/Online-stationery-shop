package com.example.onlinestationeryshop;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Integer> states;

    public ImageAdapter(Context context, int resource, ArrayList<Integer> states) {
        super(context, resource, states);
        this.states = states;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("guydsnuyais");

        View view=inflater.inflate(this.layout, parent, false);

        ImageView ImView = view.findViewById(R.id.ima);

        Integer state = states.get(position);

        ImView.setImageResource(state);
        return view;
    }
}