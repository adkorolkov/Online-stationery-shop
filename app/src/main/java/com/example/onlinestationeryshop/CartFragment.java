package com.example.onlinestationeryshop;

import android.annotation.SuppressLint;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.ActivityMainBinding;
import com.example.onlinestationeryshop.databinding.FragmentCartBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CartFragmentViewModel mViewModel;
    LinearLayout cartLatout;
    LinearLayout nullCart;
    LinearLayout payLayout;
    private FragmentCartBinding binding;
    TextView price;
    ArrayList<Good>  listitem;
    private TextView count_cart;
    TextView countQ;
    private Server server;
    private CartRecuclerViewAdapter adapter;
    private RecyclerView recyclerView;


    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void updateAdapter(ArrayList<Good>  listitem){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartRecuclerViewAdapter(getContext(), listitem);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("ResourceType")
    void fillData(ArrayList<Good> e) {
        Log.d("qqq", e.toString());
        listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
    }
    private void updateCart(){
        server = Server.getInstance(getActivity().getApplicationContext());
        if (server.getCartCount()>0) {
            cartLatout.setVisibility(View.VISIBLE);
            nullCart.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            count_cart.setText(server.getCartCount().toString());
            System.out.println("getPrice  " + Integer.toString(server.getCartPrice()));
            price.setText(Integer.toString(server.getCartPrice()) + " ₽");
            countQ.setText(Integer.toString(server.getCartCount()) + " товаров");
        }
        else{
            cartLatout.setVisibility(View.GONE);
            nullCart.setVisibility(View.VISIBLE);
            payLayout.setVisibility(View.GONE);
            count_cart.setText("");
            price.setText("");
            countQ.setText("");
        }
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
                System.out.println(intent.getStringExtra(adapter.INFO) +  "  " +   intent.getStringExtra(adapter.INFO).equals("0"));
                String h = intent.getStringExtra(adapter.INFO);
                int index = Integer.parseInt(h);
                if (index==-1){
                    updateCart();
                }
                else {
                    System.out.println("DELETE in BROADCAST " + index);
                    server.deleteCartItem(index);
                    fillData(server.getCartItems());
                    updateAdapter(listitem);
                    updateCart();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        server = Server.getInstance(getActivity().getApplicationContext());
        listitem = new ArrayList<Good>();
        cartLatout = binding.cartLayout;
        count_cart = getActivity().findViewById(R.id.count_cart);
        nullCart = binding.nullcart;
        payLayout = binding.payLayout;
        price = binding.priceAll;
        countQ = binding.countTotal;
        updateCart();
        recyclerView = binding.cartList;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                server.getCartItems();
            }
        };
        runnable.run();
        fillData(server.getCartItems());
        updateAdapter(listitem);
        Button pay = binding.pay;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addToOrder();
                mViewModel.sendMessage();
                Toast.makeText(getContext().getApplicationContext(), "Оплачено, следите за статусом заказа", Toast.LENGTH_SHORT).show();
                server.setNullCart();
                updateCart();
            }
        });
        getActivity().registerReceiver(receiver, new IntentFilter(adapter.CHANNEL));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(CartFragmentViewModel.class);
        // TODO: Use the ViewModel
    }
}