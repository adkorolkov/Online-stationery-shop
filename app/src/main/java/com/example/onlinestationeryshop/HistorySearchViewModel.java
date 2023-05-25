package com.example.onlinestationeryshop;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchViewModel extends AndroidViewModel {
    public HistorySearchViewModel(@NonNull Application application) {
        super(application);
    }

    // TODO: Implement the ViewModel

    public ArrayList<History> fillDatas() {
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.fillDatas();
    }


    public void deleteAll(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.deleteAllHistory();
    }


    public void setSearch(String search){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.setSearch(search);
    }


}