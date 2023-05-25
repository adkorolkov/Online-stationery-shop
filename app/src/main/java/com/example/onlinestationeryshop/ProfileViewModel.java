package com.example.onlinestationeryshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

public class ProfileViewModel extends AndroidViewModel {

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel
    public void exitProfile(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        server.setSearch("");
        server.deleteConfig("enter");
    }

    public String getName(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        String em = server.getConfig("email");
        if (em==null){
            return "";
        }
        else {
            return em.substring(0, 3);
        }
    }

}