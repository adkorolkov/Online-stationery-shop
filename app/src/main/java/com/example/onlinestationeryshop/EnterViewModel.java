package com.example.onlinestationeryshop;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.io.Closeable;

public class EnterViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel



    public EnterViewModel(@NonNull Application application) {
        super(application);
    }


    public String getGoodINFO(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.INFOGOOD;
    }

    public String getOrderINFO(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.INFOORDER;
    }

    public String getChannel(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        return server.CHANNEL;
    }

    public boolean isOrdersFilled(){
        if(IsEntered()){
            Server server = Server.getInstance(getApplication().getApplicationContext());
            String email = server.getConfig("email");
            if(email!=null) {
                server.fillOrders(email);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean IsEntered(){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        String is = server.getConfig("enter");
        if (is!=null){
            if(is.equals("true")) {
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
        Server server = Server.getInstance(getApplication().getApplicationContext());
        String configEmail = server.getConfig("email");
        if (configEmail==null){
            return false;
        }
        if (configEmail.equals(email)) {
            server.fillOrders(configEmail);
            return true;
        }
        else {
            return false;
        }
    }

    public void updateEnter(String enter){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        String configEnter = server.getConfig("enter");
        if (configEnter==null){
            server.insertConfig("enter", "true");
        }
        else {
            server.updateConfig("enter", enter);
        }
    }

    public boolean checkPassword(String password){
        Server server = Server.getInstance(getApplication().getApplicationContext());
        String configPassword = server.getConfig("password");
        if (configPassword==null){
            return false;
        }
        if (configPassword.equals(Integer.toString(password.hashCode()))) {
            return true;
        }
        else {
            return false;
        }
    }
}