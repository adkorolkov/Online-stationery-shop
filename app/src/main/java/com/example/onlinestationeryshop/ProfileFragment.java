package com.example.onlinestationeryshop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlinestationeryshop.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView icon = binding.Icon;
        try {
            String q = mViewModel.getName();
            System.out.println(q);
            icon.setText(mViewModel.getName());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button exit = binding.exitFromProfile;
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.exitProfile();
                Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                c.navigate(R.id.action_to_EnterFragment);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }
}