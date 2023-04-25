package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

public class ProfileViewModel extends AndroidViewModel {

    private Server server = Server.getInstance(getApplication().getApplicationContext());
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel
    public void exitProfile(){
        server.setSearch("");
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        configDao.delete("enter");
    }

    public String getName(){
        System.out.println("aaa");
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        Config em = configDao.getByName("email");
        if (em==null){
            return "";
        }
        else {
            String e = em.value;
            return e.substring(0, 3);
        }
    }

}