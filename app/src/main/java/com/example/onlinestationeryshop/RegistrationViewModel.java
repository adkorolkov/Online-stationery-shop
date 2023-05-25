package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

public class RegistrationViewModel extends AndroidViewModel {
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }


    // TODO: Implement the ViewModel



    public void addUser(String email, String password){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.addUser(email, password);
    }

    private boolean checkPasswords(String first, String second){
        return (first.equals(second) && first.length()>=8);
    }

    private boolean checkEmail(String email){
        return (email.contains("@") && email.contains(".") && email.length()>2);
    }

    public boolean check(String email, String first, String second){
        return (checkEmail(email) && checkPasswords(first, second));
    }
}