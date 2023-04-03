package com.example.onlinestationeryshop;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestationeryshop.databinding.ActivityInfogoodBinding;
import com.example.onlinestationeryshop.databinding.ActivityKatalogBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class InfoGoodActivity extends AppCompatActivity{

    private ActivityInfogoodBinding binding;

    private ImageAdapter imageAdapter;

    private ListView listView;

    private TextView ind;

    private TextView des;

    private Button back;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle arguments = getIntent().getExtras();
        int index = arguments.getInt("index");
        String desc = arguments.getString("description");
        ArrayList<Integer> images = (ArrayList<Integer>) arguments.get("images");
        for(int i=0;i< images.size();i++){
            System.out.println(images.get(i));
        }
        try {
            updateAdapter(images);
        }
        catch (Exception e){
            System.out.println("bbbbbbbbbbbb   " + e);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityInfogoodBinding.inflate(getLayoutInflater());
        ind = binding.index;
        des = binding.description;
        back = binding.backInfo;
        listView = binding.imagelist;
        ind.setText(Integer.toString(index));
        des.setText(desc);
        View view = binding.getRoot();
        setContentView(view);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(InfoGoodActivity.this, KatalogActivity.class);
                try{
                    startActivity(inte);
                }
                catch (Exception e){
                    System.out.println("Intent to katalog   "  + e.toString());
                }
            }
        });
    }



    private void updateAdapter(ArrayList<Integer> r){
        imageAdapter = new ImageAdapter(this, R.layout.image_item, r);
        listView.setAdapter(imageAdapter);
    }
}