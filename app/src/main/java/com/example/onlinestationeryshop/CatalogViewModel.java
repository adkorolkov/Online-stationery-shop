package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CatalogViewModel extends AndroidViewModel {
    public CatalogViewModel(@NonNull Application application) {
        super(application);
    }




    public void putInSearch(String a){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        HistoryDao historyDao = db.historyDao();
        System.out.println("aqaq"+historyDao);
        History b = historyDao.getBySeach(a);
        System.out.println("bqbq"+b);
        if (b == null || !b.quer.equals(a)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now)+"fffff");
            historyDao.insert(new History(a, dtf.format(now)));
        }
    }
}
