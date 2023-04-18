package com.example.onlinestationeryshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.FragmentCatalogBinding;
import com.example.onlinestationeryshop.databinding.FragmentInfoGoodBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoGoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoGoodFragment extends Fragment {
    private static final String ARG_PARAM1 = "IdArg";

    private ViewPager2 viewPager;

    private TextView count_cart;


    private ArrayList<Integer> images;
    private InfoGoodRecucleAdapter infoGoodRecucleAdapter;


    private String desc;
    private String name;
    private Integer price;

    private Server server = Server.getInstance();

    private Bundle bundle;

    private Integer indexGood;


    private FragmentInfoGoodBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public InfoGoodFragment() {
        // Required empty public constructor
    }


    public static InfoGoodFragment newInstance(String param1, String param2) {
        InfoGoodFragment fragment = new InfoGoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            indexGood = getArguments().getInt(ARG_PARAM1);
            desc = getArguments().getString("description");
            name = getArguments().getString("name");
            price = getArguments().getInt("price");
            images = (ArrayList<Integer>) getArguments().getIntegerArrayList("images");
            for(int i=0;i< images.size();i++){
                System.out.println(images.get(i));
            }
        }
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Integer r = indexGood;
        System.out.println("InfoGoodFragment  " + r.toString());
        TextView des = binding.description;
        TextView na = binding.text;
        TextView count = binding.count;
        count_cart = binding.countCart;
        updateCart();
        LinearLayout linearLayout = binding.tripleButton;
        ImageButton minus = binding.minus;
        ImageButton plus = binding.plus;
        ImageButton addToCart = binding.addToCart;
        linearLayout.setVisibility(View.GONE);
        count.setTextSize(30);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count.getText().toString().equals("1")){
                    count.setText("");
                    server.deleteCartItem(r);
                    updateCart();
                    linearLayout.setVisibility(View.GONE);
                    addToCart.setVisibility(View.VISIBLE);
                }
                else {
                    Integer i = Integer.parseInt(count.getText().toString()) - 1;
                    count.setText(i.toString());
                    server.changeCartItem(r, -1);
                    updateCart();
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(count.getText().toString()) + 1;
                if (i>999){
                    count.setTextSize(25);
                }
                count.setText(i.toString());
                server.changeCartItem(r, 1);
                updateCart();
            }
        });
        na.setText(name);
        na.setTextSize(30);
        TextView priceT = binding.price;
        priceT.setText(price.toString() + " ₽");
        priceT.setTextSize(20);
        des.setText(desc);
        des.setTextSize(20);
        viewPager = binding.viewpager;
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.addToCart(r, 1);
                updateCart();
                addToCart.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                count.setText("1");
            }
        });
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(getContext(), images);
        try{
            viewPager.setAdapter(viewPager2Adapter);
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                // This method is triggered when there is any scrolling activity for the current page
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                // triggered when you select a new page
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }

                // triggered when there is
                // scroll state will be changed
                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
        BottomNavigationView bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment navhost = getParentFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        switch (item.getItemId()) {
                            case R.id.action_catalog:
                                System.out.println("Каталог");
                                c.navigate(R.id.action_InfoGoodFragment_to_Catalog_Fragment);
                                break;
                            case R.id.action_books:
                                System.out.println("Заказы");
                                Toast.makeText(getContext().getApplicationContext(), "Заказы пока не работают", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_print:
                                System.out.println("Печать");
                                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, 1);
                                break;
                            case R.id.action_cart:
                                System.out.println("Корзина");
                                c.navigate(R.id.action_InfoGoodFragment_to_CartFragment);
                                break;
                            case R.id.action_profile:
                                System.out.println("Профиль");
                                Toast.makeText(getContext().getApplicationContext(), "Профиль пока не работает", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
    }


    private void updateCart(){
        if (server.getCartCount()>0) {
            count_cart.setText(server.getCartCount().toString());
        }
        else{
            count_cart.setText("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoGoodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}