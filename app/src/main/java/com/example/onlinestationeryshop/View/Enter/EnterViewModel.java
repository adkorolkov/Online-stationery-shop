package com.example.onlinestationeryshop.View.Enter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;

public class EnterViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel



    public EnterViewModel(@NonNull Application application) {
        super(application);
    }


    public String getGoodINFO(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOGOOD;
    }

    public String getOrderINFO(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOORDER;
    }

    public String getChannel(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.CHANNEL;
    }

    public boolean isOrdersFilled(){
        if(IsEntered()){
            Repository repository = Repository.getInstance(getApplication().getApplicationContext());
            String email = repository.getConfig("email");
            if(email!=null) {
                repository.fillOrders(email);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean IsEntered(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        String is = repository.getConfig("enter");
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
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        String configEmail = repository.getConfig("email");
        if (configEmail==null){
            return false;
        }
        if (configEmail.equals(email)) {
            repository.fillOrders(configEmail);
            return true;
        }
        else {
            return false;
        }
    }

    public void updateEnter(String enter){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        String configEnter = repository.getConfig("enter");
        if (configEnter==null){
            repository.insertConfig("enter", "true");
        }
        else {
            repository.updateConfig("enter", enter);
        }
    }

    public boolean checkPassword(String password){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        String configPassword = repository.getConfig("password");
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