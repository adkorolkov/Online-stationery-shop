package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.io.Closeable;

public class EnterViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private GetDataBase gdt;

    public EnterViewModel(@NonNull Application application) {
        super(application);
    }


    public boolean IsEntered(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        System.out.println("start IsEntered");
        ConfigDao configDao = db.configDao();
        System.out.println("get db");
        Config is = configDao.getByName("enter");
        if (is!=null){
            if(is.value.equals("true")) {
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }
    public boolean checkEmail(String email){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        Config configEmail = configDao.getByName("email");
        if (configEmail==null){
            return false;
        }
        System.out.println(configEmail + " aboba");
        if (configEmail.value.equals(email)) {
            Server server = Server.getInstance(getApplication().getApplicationContext());
            server.fillOrders(configEmail.value);
            return true;
        }
        else {
            return false;
        }
    }

    public void updateEnter(String enter){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        Config configEnter = configDao.getByName("enter");
        if (configEnter==null){
            configDao.insert(new Config("enter", "true"));
        }
        else {
            configEnter.value = enter;
            configDao.update(configEnter);
        }
    }

    public boolean checkPassword(String password){
        DataBase db = Room.databaseBuilder(getApplication().getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
        ConfigDao configDao = db.configDao();
        Config configPassword = configDao.getByName("password");
        if (configPassword==null){
            return false;
        }
        if (configPassword.value.equals(password)) {
            return true;
        }
        else {
            return false;
        }
    }
}