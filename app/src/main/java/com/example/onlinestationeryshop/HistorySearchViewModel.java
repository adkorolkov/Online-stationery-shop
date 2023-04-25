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

    private Server server = Server.getInstance(getApplication().getApplicationContext());
    // TODO: Implement the ViewModel

    public ArrayList<History> fillDatas() {
        System.out.println("");
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        HistoryDao historyDao = db.historyDao();
        List<History> history = historyDao.getLast(20);
        System.out.println("List<History len  >" + history.size());
        return fillData(history);
    }


    public void deleteAll(){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        HistoryDao historyDao = db.historyDao();
        historyDao.deleteAll();
    }

    private ArrayList<History> fillData(List<History> e) {
        ArrayList<History> listitem = new ArrayList<>();
        for(int i=0;i< e.size();i++) {
            listitem.add(e.get(i));
        }
        return listitem;
    }

    public void setSearch(String search){
        server.setSearch(search);
    }


}