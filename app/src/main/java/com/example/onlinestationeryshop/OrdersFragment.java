package com.example.onlinestationeryshop;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinestationeryshop.databinding.FragmentOrdersBinding;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel mViewModel;
    private FragmentOrdersBinding binding;
    OrderRecuclerViewAdapter orderRecuclerViewAdapter;
    CartFragmentViewModel cartFragmentViewModel;
    List<FilledOrder> listitem;
    private RecyclerView recyclerView;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }


    @SuppressLint("ResourceType")
    void fillData(List<FilledOrder> e) {
        listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
    }


    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                System.out.println(intent.getStringExtra(orderRecuclerViewAdapter.INFO) +  "  " +   intent.getStringExtra(orderRecuclerViewAdapter.INFO).equals("0"));
                String h = intent.getStringExtra(orderRecuclerViewAdapter.INFO);
                String t = intent.getStringExtra(cartFragmentViewModel.INFO);
                if (h.equals("true")){
                    update();
                }
                else {
                    if (h.endsWith("Cansel")) {
                        int id = Integer.parseInt(h.split(" ")[0]);
                        mViewModel.changeStatusOrder("Отменён", id);
                        update();
                    }
                    else if (h.endsWith("Ready")){
                        int id = Integer.parseInt(h.split(" ")[0]);
                        mViewModel.changeStatusOrder("Получен", id);
                        update();
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    protected BroadcastReceiver receiverTimer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                System.out.println(intent.getStringExtra(cartFragmentViewModel.INFO) +  " aboba " +   intent.getStringExtra(cartFragmentViewModel.INFO).equals("0"));
                String h = intent.getStringExtra(cartFragmentViewModel.INFO);
                if (h.equals("change")){
                    update();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void updateAdapter(List<FilledOrder>  listitem){
        System.out.println("len listitem in updateAdapter  " + listitem.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderRecuclerViewAdapter = new OrderRecuclerViewAdapter(getContext(), listitem);
        recyclerView.setAdapter(orderRecuclerViewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void update(){
        fillData(mViewModel.getFilledOrders());
        updateAdapter(listitem);
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
        getActivity().registerReceiver(receiver, new IntentFilter(orderRecuclerViewAdapter.CHANNEL));
        getActivity().registerReceiver(receiverTimer, new IntentFilter(cartFragmentViewModel.CHANNEL));
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = binding.orders;
        listitem = new ArrayList<>();
        updateAdapter(listitem);

    }
        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(OrdersViewModel.class);
        cartFragmentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(CartFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

}