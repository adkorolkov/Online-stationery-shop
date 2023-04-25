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
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinestationeryshop.databinding.FragmentRegistrationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    private RegistrationViewModel mViewModel;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
        EditText email = binding.email;
        EditText firstpassword = binding.password;
        EditText secondpassword = binding.repeatpassword;
        Button registration = binding.enter;
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstpassword.getText().toString();
                String second = secondpassword.getText().toString();
                String mail = email.getText().toString();
                if (first.equals(second) && mail.length()>2 && mail.contains("@")){
                    mViewModel.addUser(email.getText().toString(), first);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    c.navigate(R.id.action_to_CatalogFragment);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Пароли не совпадают или неправильно указана почта", Toast.LENGTH_SHORT).show();
                    firstpassword.setText("");
                    secondpassword.setText("");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication()).create(RegistrationViewModel.class);
        // TODO: Use the ViewModel
    }

}