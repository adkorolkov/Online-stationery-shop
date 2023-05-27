package com.example.onlinestationeryshop.View.Catalog;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinestationeryshop.R;
import com.example.onlinestationeryshop.View.MainActivity;
import com.example.onlinestationeryshop.View.Catalog.OnItemClickListener;
import com.example.onlinestationeryshop.databinding.FragmentCatalogBinding;
import com.example.onlinestationeryshop.models.Good;

import java.util.ArrayList;


public class CatalogFragment extends Fragment  implements OnItemClickListener {






    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView count_cart;
    private CatalogViewModel mViewModel;
    private MainActivity mainActivity = new MainActivity();



    private FragmentCatalogBinding mbinding;
    private TextView nulladapter;

    private EditText editText;



    GoodRycycleAdapter goodAdapter;
    ArrayList<Good>  listitem;
    private Button back;
    private Button cancel;
    private Button search;
    private RecyclerView recyclerView;


    private void updateAdapter(ArrayList<Good>  listitem){
        try {
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
    public void onClick(int goodId) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("IdArg", goodId);
            bundle.putString("description", mViewModel.getForId(goodId).getDescription());
            bundle.putString("name", mViewModel.getForId(goodId).getName());
            bundle.putInt("price", mViewModel.getForId(goodId).getPrice());
            bundle.putStringArrayList("images", mViewModel.getImageList(goodId));
            Fragment navhost = getParentFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
            NavController c = NavHostFragment.findNavController(navhost);
            c.navigate(R.id.action_to_InfoGoodFragment, bundle);
        }
        catch (Exception e){
        }
    }


    public void search(View view){
        String text = editText.getText().toString();
        text = text.toLowerCase();
        text = text.trim();
        mViewModel.setSearch(text);
        if (!text.equals("")) {
            mViewModel.putInSearch(text);
        }
        editText.setText("");
        hideKeyboardFrom(view.getContext(),view);
        FocusOff(editText);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mViewModel.searchGoods();
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




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected BroadcastReceiver receiverq = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String h = intent.getStringExtra(mViewModel.getGoodInfo());
                if (h.equals("ReadyGo")) {
                    fillData(mViewModel.search());
                    updateAdapter(listitem);
                }
            } catch (Exception e) {

            }
        }
    };
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String h = intent.getStringExtra(goodAdapter.INFO);
                if(h!=null) {
                    updateCart();
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
            System.out.println(e.fillInStackTrace());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mViewModel.searchGoods();
        fillData(mViewModel.search());
        updateAdapter(listitem);
        getActivity().registerReceiver(receiver, new IntentFilter(goodAdapter.CHANNEL));
        getActivity().registerReceiver(receiverq, new IntentFilter(mViewModel.getChannel()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listitem  = new ArrayList<Good>();
        count_cart = getActivity().findViewById(R.id.count_cart);
        nulladapter = mbinding.nullAdapter;
        editText = mbinding.search;
        back = mbinding.back;
        cancel = mbinding.cancel;
        updateCart();
        search = mbinding.searchbt;
        recyclerView = mbinding.goodsrecyclerlist;
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
                mViewModel.setSearch("");
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