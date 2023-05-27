package com.example.onlinestationeryshop.View.Registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;

public class RegistrationViewModel extends AndroidViewModel {
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }


    // TODO: Implement the ViewModel



    public void addUser(String email, String password){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.addUser(email, password);
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