package com.example.onlinestationeryshop.View.HistorySearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;
import com.example.onlinestationeryshop.models.History;

import java.util.ArrayList;

public class HistorySearchViewModel extends AndroidViewModel {
    public HistorySearchViewModel(@NonNull Application application) {
        super(application);
    }

    // TODO: Implement the ViewModel

    public ArrayList<History> fillDatas() {
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.fillDatas();
    }


    public void deleteAll(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.deleteAllHistory();
    }


    public void setSearch(String search){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.setSearch(search);
    }


}