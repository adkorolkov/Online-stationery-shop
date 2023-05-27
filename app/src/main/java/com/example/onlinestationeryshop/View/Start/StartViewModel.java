package com.example.onlinestationeryshop.View.Start;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;

public class StartViewModel extends AndroidViewModel {
    public StartViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel


    public String getGoodInfo(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOGOOD;
    }

    public String getChannel(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.CHANNEL;
    }

    public void searchGoods(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.searchgoods();
    }
}