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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.ActivityMainBinding;
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
    TextView count_cart;
    private CatalogViewModel mViewModel;
    private MainActivity mainActivity = new MainActivity();


    private FragmentCatalogBinding mbinding;
    private TextView nulladapter;
    ArrayList<ArrayList<Integer>> cart;

    private EditText editText;

    Server server;


    private View CatView;
    BottomNavigationView bottomNavigationView;
    GoodRycycleAdapter goodAdapter;
    ArrayList<Good>  listitem;
    private Button back;
    private Button cancel;
    private Button search;
    private RecyclerView recyclerView;


    private void updateAdapter(ArrayList<Good>  listitem){
        try {
            System.out.println(listitem.size() + "eeee");
            if (listitem.size() > 0) {
                nulladapter.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                goodAdapter = new GoodRycycleAdapter(getContext(), listitem, this::onClick);
                recyclerView.setAdapter(goodAdapter);

            } else {
                goodAdapter = new GoodRycycleAdapter(getContext(), listitem, this::onClick);
                recyclerView.setVisibility(View.GONE);
                nulladapter.setVisibility(View.VISIBLE);
            }
            cancel.setVisibility(View.GONE);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @SuppressLint("ResourceType")
    public void onClick(int i) {
        Log.d("qqq","Нажата область товара  " + i);
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("IdArg", i);
            bundle.putString("description", server.getForInd(i).getDescription());
            bundle.putString("name", server.getForInd(i).getName());
            bundle.putInt("price", server.getForInd(i).getPrice());
            ArrayList<Integer> images = new ArrayList<>();
            List<Integer> r = server.getForInd(i).getImages();
            for (int x=0;x<r.size();x++){
                images.add(r.get(x));
            }
            bundle.putIntegerArrayList("images", images);
            Fragment navhost = getParentFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
            NavController c = NavHostFragment.findNavController(navhost);
            c.navigate(R.id.action_to_InfoGoodFragment, bundle);
        }
        catch (Exception e){
            System.out.println("bbb"+e);
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
        text = text.trim();
        server.setSearch(text);
        if (!text.equals("")) {
            mViewModel.putInSearch(text);
        }
        editText.setText("");
        hideKeyboardFrom(view.getContext(),view);
        FocusOff(editText);
        System.out.println(text + "aaa");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                server.searchgoods();
            }
        };
        runnable.run();
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

    protected BroadcastReceiver receiverq = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("qqq", "onReceive()");
            try {
                Log.d("qqq", "Broadcaststart");
                Server server = Server.getInstance(getActivity().getApplicationContext());
                String h = intent.getStringExtra(server.INFOGOOD);
                Log.d("qqq", h);
                if (h.equals("Ready")) {
                    fillData(server.search());
                    updateAdapter(listitem);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.d("qqq", "Broadcast");
                server = Server.getInstance(getActivity().getApplicationContext());
                System.out.println(server+"sss");
                System.out.println(intent.getStringExtra(goodAdapter.INFO) +  "  " +   intent.getStringExtra(goodAdapter.INFO).equals("0"));
                String h = intent.getStringExtra(goodAdapter.INFO);
                int index = Integer.parseInt(h);
                Log.d("qqq", "Добавление в корзину" + h);
                boolean ok = false;
                updateCart();
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

    private void updateCart(){
        try {
            mainActivity.updateCart(count_cart, getActivity().getApplicationContext());
        }
        catch (Exception e){
            System.out.println(e.fillInStackTrace() + "qqq");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        server = Server.getInstance(getActivity().getApplicationContext());
        fillData(server.search());
        updateAdapter(listitem);
        Log.d("qqq", "rrrrr"+server.orders.size());
        getActivity().registerReceiver(receiver, new IntentFilter(goodAdapter.CHANNEL));
        getActivity().registerReceiver(receiverq, new IntentFilter(server.CHANNEL));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listitem  = new ArrayList<Good>();
        count_cart = getActivity().findViewById(R.id.count_cart);
        nulladapter = mbinding.nullAdapter;
        editText = mbinding.search;
        server = Server.getInstance(getActivity().getApplicationContext());
        server.searchgoods();
        Log.d("qqq", editText.toString());
        back = mbinding.back;
        cancel = mbinding.cancel;
        updateCart();
        search = mbinding.searchbt;
        recyclerView = mbinding.goodsrecyclerlist;
        cart = new ArrayList<>();
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
                server.setSearch("");
                search(view);
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
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(CatalogViewModel.class);
        // TODO: Use the ViewModel
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mbinding = FragmentCatalogBinding.inflate(inflater, container, false);
        return mbinding.getRoot();
    }
}