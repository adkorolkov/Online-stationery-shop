package com.example.onlinestationeryshop.View.Cart;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.R;
import com.example.onlinestationeryshop.databinding.FragmentCartBinding;
import com.example.onlinestationeryshop.models.Good;

import java.util.ArrayList;

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
    private CartRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;


    public CartFragment() {
        // Required empty public constructor
    }




    private void updateAdapter(ArrayList<Good>  listitem){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartRecyclerViewAdapter(getContext(), listitem);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("ResourceType")
    void fillData(ArrayList<Good> e) {
        listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
    }
    private void updateCart(){
        if (mViewModel.getCartCount()>0) {
            cartLatout.setVisibility(View.VISIBLE);
            nullCart.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            count_cart.setText(mViewModel.getCartCount().toString());
            price.setText(mViewModel.getCartPrice() + " ₽");
            countQ.setText(mViewModel.getCartCount() + " товаров");
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
                String h = intent.getStringExtra(adapter.INFO);
                int index = Integer.parseInt(h);
                if (index==-1){
                    updateCart();
                }
                else {
                    mViewModel.deleteCartItem(index);
                    fillData(mViewModel.getCartItems());
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
    public void onStart() {
        super.onStart();
        updateCart();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fillData(mViewModel.getCartItems());
            }
        };
        runnable.run();
        updateAdapter(listitem);
        getActivity().registerReceiver(receiver, new IntentFilter(adapter.CHANNEL));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listitem = new ArrayList<Good>();
        cartLatout = binding.cartLayout;
        count_cart = getActivity().findViewById(R.id.count_cart);
        nullCart = binding.nullcart;
        payLayout = binding.payLayout;
        price = binding.priceAll;
        countQ = binding.countTotal;
        recyclerView = binding.cartList;
        Button pay = binding.pay;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addToOrder();
                mViewModel.sendMessage();
                Toast.makeText(getContext().getApplicationContext(), "Оплачено, следите за статусом заказа", Toast.LENGTH_SHORT).show();
                mViewModel.setNullCart();
                updateCart();
            }
        });
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