package com.example.onlinestationeryshop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlinestationeryshop.databinding.FragmentHistorySearchBinding;

import java.util.ArrayList;

public class HistorySearchFragment extends Fragment implements OnSeacrhItemClickListener{

    private HistorySearchViewModel mViewModel;
    ArrayList<History> listitem;
    private Button delete;
    private TextView nullSearch;
    private RecyclerView recyclerView;
    SeacrhRecucleAdapter goodAdapter;

    private FragmentHistorySearchBinding binding;

    public static HistorySearchFragment newInstance() {
        return new HistorySearchFragment();
    }



    public void updateAdapter(ArrayList<History> listitem){
        try {
            listitem = mViewModel.fillDatas();
            if (listitem.size()==0){
                delete.setVisibility(View.GONE);
                nullSearch.setVisibility(View.VISIBLE);
            }
            else {
                delete.setVisibility(View.VISIBLE);
                nullSearch.setVisibility(View.GONE);
                System.out.println(listitem.size()+"ыыы");
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            goodAdapter = new SeacrhRecucleAdapter(getContext(), listitem, this::onClick);
            recyclerView.setAdapter(goodAdapter);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistorySearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAdapter(listitem);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listitem = new ArrayList<History>();
        delete = binding.deleteHistory;
        recyclerView = binding.searcHistory;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteAll();
                updateAdapter(listitem);
            }
        });
        nullSearch = binding.nullSearch;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(HistorySearchViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(String i) {
        mViewModel.setSearch(i);
        Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        NavController c = NavHostFragment.findNavController(navhost);
        c.navigate(R.id.action_to_CatalogFragment);
    }
}