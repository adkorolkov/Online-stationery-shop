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
import androidx.room.Room;

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

import com.example.onlinestationeryshop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity {
    public Server getServer() {
        return server;
    }
    public MainActivity MainActivity(){
        return this;
    }
    private ActivityMainBinding mBinding;
    private TextView count_cart;

    private Server server = Server.getInstance();


    //NavController navControllerStart = Navigation.findNavController(this, R.id.action_placeholder_to_CatalogFragment);
    //NavController navControllerInfo = Navigation.findNavController(this, R.id.action_CatalogFragment_to_InfoGoodFragment);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        count_cart = mBinding.countCart;
        updateCart(count_cart);
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment navhost = getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        switch (item.getItemId()) {
                            case R.id.action_catalog:
                                System.out.println("Каталог");
                                try {
                                    c.navigate(R.id.action_to_CatalogFragment);
                                }
                                catch (Exception e){

                                }
                                break;
                            case R.id.action_books:
                                System.out.println("Заказы");
                                Toast.makeText(getApplicationContext(), "Заказы пока не работают", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_print:
                                System.out.println("Печать");
                                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 1);
                                break;
                            case R.id.action_cart:
                                try {
                                    c.navigate(R.id.action_to_CartFragment);
                                }
                                catch (Exception e){

                                }
                                break;
                            case R.id.action_profile:
                                System.out.println("Профиль");
                                try{
                                    c.navigate(R.id.action_to_Profile);
                                }
                                catch (Exception e){

                                }
                                break;
                        }
                        return false;
                    }
                });
        setContentView(mBinding.getRoot());

    }
    public void updateCart(TextView count_carte){
        if (server.getCartCount()>0) {
            count_carte.setText(server.getCartCount().toString());
        }
        else{
            System.out.println(count_carte+"vv");
            count_carte.setText("");
        }
    }
}