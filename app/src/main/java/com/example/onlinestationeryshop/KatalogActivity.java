package com.example.onlinestationeryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextParams;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.onlinestationeryshop.databinding.ActivityKatalogBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class KatalogActivity extends AppCompatActivity implements OnItemClickListener {




    private ListView imageListTest;

    private EditText editText;
    Server server;
    BottomNavigationView bottomNavigationView;
    GoodAdapter goodAdapter;
    ArrayList<Good>  listitem;
    private Button back;
    private Button cancel;
    private Button search;
    private ListView listView;
    private ActivityKatalogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKatalogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        listitem  = new ArrayList<Good>();
        editText = binding.search;
        back = binding.back;
        cancel = binding.cancel;
        search = binding.searchbt;
        listView = binding.goodslist;
        imageListTest = binding.imagelisttest;
        bottomNavigationView = binding.bottomNavigation;
        ArrayList<Good> e = new ArrayList<>();
        ArrayList<Integer> images_tr = new ArrayList<>();
        images_tr.add(R.drawable.tractor);
        images_tr.add(R.drawable.orange_will);
        images_tr.add(R.drawable.red_will);
        images_tr.add(R.drawable.will_gorizont);

        ArrayList<Integer> images_mi = new ArrayList<>();
        images_mi.add(R.drawable.mishe);
        images_mi.add(R.drawable.orange_mi);
        images_mi.add(R.drawable.heart_mi);
        images_mi.add(R.drawable.circle_mi);
        for (int i = 0; i < 20; i++) {
            e.add(new Good(R.drawable.mishe, "Суперская игровая мышь, которая позволит нагибать всех ботов", 1500, "Мышь компьютерная", 2*i, "В комплекте поставляется 2 мышки, в красном и белом варианте, чтобы можно было делиться с другом как Польшой. Также много кнопок - целых 3", images_mi));
            e.add(new Good(R.drawable.tractor, "Колесо трактора, лучший транспорт", 19000, "Колесо трактора", 2*i+1, "Колесо трактора - лучший транспорт до вуза, быстрее метро", images_tr));
        }
        server = new Server(e);
        fillData(server.search(""));
        updateAdapter(listitem);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    cancel.setVisibility(View.VISIBLE);
                }
                else{
                    cancel.setVisibility(View.GONE);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search(view);
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                hideKeyboardFrom(view.getContext(), view);
                FocusOff(editText);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               search(view);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_books:
                                System.out.println("Заказы");
                                break;
                            case R.id.action_print:
                                System.out.println("Печать");
                                break;
                            case R.id.action_cart:
                                System.out.println("Корзина");
                                break;
                        }
                        return false;
                    }
                });

    }


    @SuppressLint("ResourceType")
    void fillData(ArrayList<Good> e) {
        listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
    }



    private void updateAdapter(ArrayList<Good>  listitem){
        goodAdapter = new GoodAdapter(this, listitem, this);
        listView.setAdapter(goodAdapter);
        cancel.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int i) {
        // обрабатываете здесь ваши данные
        System.out.println("Нажата область товара  ");
        try{
            Intent intent = new Intent(this, InfoGoodActivity.class);
            intent.putExtra("index", i);
            System.out.println(server.getForInd(i).getName());
            intent.putExtra("description", server.getForInd(i).getDescription());
            intent.putExtra("images", server.getForInd(i).getImages());
            startActivity(intent);
        }
        catch (Exception e){
            System.out.println("intent  " + e.toString());
        }
    }


    public void search(View view){
        String text = editText.getText().toString();
        text = text.toLowerCase();
        editText.setText("");
        hideKeyboardFrom(view.getContext(),view);
        FocusOff(editText);
        System.out.println(text + "aaa");
        if(text.length()>0) {
            fillData(server.search(text));
            updateAdapter(listitem);
        }
    }

    public static void FocusOff(EditText e){
        e.clearFocus();
        e.setCursorVisible(false);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}