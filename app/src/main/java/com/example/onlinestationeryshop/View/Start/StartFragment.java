package com.example.onlinestationeryshop.View.Start;

import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinestationeryshop.R;

public class StartFragment extends Fragment {

    private StartViewModel mViewModel;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String h = intent.getStringExtra(mViewModel.getGoodInfo());
                if (h.equals("ReadyGo")) {
                    Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    c.navigate(R.id.action_to_EnterFragment);
                }
            } catch (Exception e) {

            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(receiver, new IntentFilter(mViewModel.getChannel()));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                mViewModel.searchGoods();
            }
        };
        runnable.run();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(StartViewModel.class);
        // TODO: Use the ViewModel
    }

}