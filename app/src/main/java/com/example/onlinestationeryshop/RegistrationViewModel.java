package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

public class RegistrationViewModel extends AndroidViewModel {
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }


    // TODO: Implement the ViewModel



    public void addUser(String email, String password){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        configDao.deleteAll();
        configDao.insert(new Config("email", email));
        configDao.insert(new Config("password", password));
        configDao.insert(new Config("enter", "true"));
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.fillOrders(email);
    }
}