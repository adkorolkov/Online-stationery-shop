package com.example.onlinestationeryshop.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.onlinestationeryshop.R;
import com.example.onlinestationeryshop.Repository;
import com.example.onlinestationeryshop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity  extends AppCompatActivity {
    public Repository getrepository() {
        return repository;
    }
    public MainActivity MainActivity(){
        return this;
    }
    private ActivityMainBinding mBinding;
    private TextView count_cart;

    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        repository =  Repository.getInstance(getApplicationContext());
        count_cart = mBinding.countCart;
        updateCart(count_cart, getApplication().getApplicationContext());
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment navhost = getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        switch (item.getItemId()) {
                            case R.id.action_catalog:
                                try {
                                    c.navigate(R.id.action_to_CatalogFragment);
                                }
                                catch (Exception e){

                                }
                                break;
                            case R.id.action_books:
                                try {
                                    c.navigate(R.id.action_to_OrderFragment);
                                }
                                catch (Exception e){

                                }
                                break;
                            case R.id.action_print:
                                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                photoPickerIntent.setType("file/*");
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
    public void updateCart(TextView count_carte, Context context){
        repository = Repository.getInstance(context);
        if (repository.getCartCount()>0) {
            count_carte.setText(repository.getCartCount().toString());
        }
        else{
            count_carte.setText("");
        }
    }
}