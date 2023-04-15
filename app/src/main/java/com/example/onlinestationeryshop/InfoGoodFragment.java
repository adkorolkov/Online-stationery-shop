package com.example.onlinestationeryshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlinestationeryshop.databinding.ActivityInfogoodBinding;
import com.example.onlinestationeryshop.databinding.FragmentCatalogBinding;
import com.example.onlinestationeryshop.databinding.FragmentInfoGoodBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoGoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoGoodFragment extends Fragment {
    private static final String ARG_PARAM1 = "IdArg";

    private ArrayList<Integer> images;
    private InfoGoodRecucleAdapter infoGoodRecucleAdapter;

    private RecyclerView recyclerView;

    private String desc;

    private Bundle bundle;

    private Integer indexGood;

    private Button back;

    private FragmentInfoGoodBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private void updateAdapter(ArrayList<Integer> r){
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        infoGoodRecucleAdapter = new InfoGoodRecucleAdapter(getContext(), r);
        recyclerView.setAdapter(infoGoodRecucleAdapter);
    }

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
        des.setText(desc);
        back = binding.backInfo;
        recyclerView = binding.imagelist;
        try{
            updateAdapter(images);
        }
        catch (Exception e){
            System.out.println(e);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment navhost = getParentFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                c.navigate(R.id.action_InfoGoodFragment_to_Catalog_Fragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoGoodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}