package com.example.onlinestationeryshop.View.Profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;

public class ProfileViewModel extends AndroidViewModel {

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel
    public void exitProfile(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.setSearch("");
        repository.deleteConfig("enter");
    }

    public String getName(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        String em = repository.getConfig("email");
        if (em==null){
            return "";
        }
        else {
            return em.substring(0, 3);
        }
    }

}