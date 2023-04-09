package com.example.onlinestationeryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.ActivityKatalogBinding;
import com.example.onlinestationeryshop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    //NavController navControllerStart = Navigation.findNavController(this, R.id.action_placeholder_to_CatalogFragment);
    //NavController navControllerInfo = Navigation.findNavController(this, R.id.action_CatalogFragment_to_InfoGoodFragment);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());
       /* try {
            NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
            NavController a = hostFragment.getNavController();
            NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, a);
        }
        catch (Exception e){
            System.out.println("qqq"+e);
        }
        */
    }
}