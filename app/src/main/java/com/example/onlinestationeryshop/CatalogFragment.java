package com.example.onlinestationeryshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.ActivityKatalogBinding;
import com.example.onlinestationeryshop.databinding.FragmentCatalogBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class CatalogFragment extends Fragment  implements OnItemClickListener{






    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private FragmentCatalogBinding mbinding;
    ArrayList<ArrayList<Integer>> cart;

    private EditText editText;

    Server server;

    BottomNavigationView bottomNavigationView;
    GoodRycycleAdapter goodAdapter;
    ArrayList<Good>  listitem;
    private Button back;
    private Button cancel;
    private Button search;
    private RecyclerView recyclerView;


    private void updateAdapter(ArrayList<Good>  listitem){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        goodAdapter = new GoodRycycleAdapter(getContext(), listitem, this::onClick);
        recyclerView.setAdapter(goodAdapter);
        cancel.setVisibility(View.GONE);
    }

    @SuppressLint("ResourceType")
    public void onClick(int i) {
        System.out.println("Нажата область товара  " + i);
        try{
        }
        catch (Exception e){
            System.out.println("aaaaaaaaa"+e);
        }
        // обрабатываете здесь ваши данные

       /* try{
            Intent intent = new Intent(this, InfoGoodActivity.class);
            intent.putExtra("index", i);
            System.out.println(server.getForInd(i).getName());
            intent.putExtra("description", server.getForInd(i).getDescription());
            intent.putExtra("images", server.getForInd(i).getImages());
            startActivity(intent);
        }
        catch (Exception e){
            System.out.println("intent  " + e.toString());
        }*/
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


    public static CatalogFragment newInstance(String param1, String param2) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                System.out.println(intent.getStringExtra(goodAdapter.INFO) +  "  " +   intent.getStringExtra(goodAdapter.INFO).equals("0"));
                String h = intent.getStringExtra(goodAdapter.INFO);
                int index = Integer.parseInt(h);
                boolean ok = false;
                System.out.println("Размер cart " + cart.size());
                if(cart.size()==0){
                    ArrayList<Integer> b = new ArrayList<>();
                    b.add(index);
                    b.add(1);
                    cart.add(b);
                }
                else {
                    for (int i = 0; i < cart.size(); i++) {
                        if (cart.get(i).get(0) == index) {
                            ok = true;
                            int a = cart.get(i).get(1);
                            a++;
                            cart.get(i).set(1, a);
                        }
                    }
                    if (!ok) {
                        ArrayList<Integer> b = new ArrayList<>();
                        b.add(index);
                        b.add(1);
                        cart.add(b);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public CatalogFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceType")
    void fillData(ArrayList<Good> e) {
        listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            //NavController navController = Navigation.findNavController(view);
            //navController.navigate(R.id.action_CatalogFragment_to_InfoGoodFragment);
        }
        catch (Exception e){
            System.out.println("bbb"+e);
        }
        listitem  = new ArrayList<Good>();
        editText = mbinding.search;
        back = mbinding.back;
        cancel = mbinding.cancel;
        search = mbinding.searchbt;
        recyclerView = mbinding.goodsrecyclerlist;
        bottomNavigationView = mbinding.bottomNavigation;
        cart = new ArrayList<>();
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
        getActivity().registerReceiver(receiver, new IntentFilter(goodAdapter.CHANNEL));
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
                                for(int i=0;i< cart.size();i++){
                                    ArrayList<Integer> q = cart.get(i);
                                    String w = "Позиция " + (q.get(0) +1)+ "   " + q.get(1) + "штук";
                                    Toast.makeText(getActivity().getApplicationContext(),w, Toast.LENGTH_SHORT).show();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                break;
                        }
                        return false;
                    }
                });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mbinding = FragmentCatalogBinding.inflate(inflater, container, false);
        return mbinding.getRoot();
    }
}