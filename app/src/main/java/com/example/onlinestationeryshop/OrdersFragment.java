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

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinestationeryshop.databinding.FragmentOrdersBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel mViewModel;

    private Server server;
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
                Log.d("qqq", h);
                if (h.equals("true")){
                    update();
                }
                else {
                    if (h.endsWith("Cansel")) {
                        int id = Integer.parseInt(h.split(" ")[0]);
                        mViewModel.changeStatusOrder("Отменён", id);
                        update();
                    }
                    else if (h.endsWith("ReadyGet")){
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

    protected BroadcastReceiver receiverStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                server = Server.getInstance(getActivity().getApplicationContext());
                String h = intent.getStringExtra(server.INFOORDER);
                if (h.equals("ChangeStatus")){
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


    private void loop(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                server = Server.getInstance(getActivity().getApplicationContext());
                server.fillOrders(mViewModel.getEmail());
                update();
            }
        }, 5000);
    }
    @Override
    public void onStart() {
        super.onStart();
        server = Server.getInstance(getActivity().getApplicationContext());
        update();
        loop();
        getActivity().registerReceiver(receiver, new IntentFilter(orderRecuclerViewAdapter.CHANNEL));
        getActivity().registerReceiver(receiverTimer, new IntentFilter(cartFragmentViewModel.CHANNEL));
        getActivity().registerReceiver(receiverStatus, new IntentFilter(server.CHANNEL));
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