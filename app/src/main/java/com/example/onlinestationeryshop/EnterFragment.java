package com.example.onlinestationeryshop;

import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.FragmentEnterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EnterFragment extends Fragment {

    private EnterViewModel mViewModel;
    private FragmentEnterBinding binding;

    public static EnterFragment newInstance() {
        return new EnterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEnterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            boolean a = mViewModel.IsEntered();
            if (a){
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setVisibility(View.VISIBLE);
                Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                c.navigate(R.id.action_to_CatalogFragment);
            }
        }
        catch (Exception e){
            System.out.println(e+"qqqe");
        }

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
        try {
            EditText email = binding.email;
            EditText password = binding.password;
            Button enter = binding.enter;
            Button regi = binding.regis;
            regi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("aaaaaaaa");
                    Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    c.navigate(R.id.action_to_RegistrationFragment);
                }
            });
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();
                    System.out.println(mViewModel.checkEmail(mail));
                    System.out.println(mViewModel.checkPassword(pass));
                    if (mViewModel.checkEmail(mail) && mViewModel.checkPassword(pass)) {
                        mViewModel.updateEnter("true");
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                        NavController c = NavHostFragment.findNavController(navhost);
                        c.navigate(R.id.action_to_CatalogFragment);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Неверные данные", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password.setText("");
                    }
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(EnterViewModel.class);
    }

}